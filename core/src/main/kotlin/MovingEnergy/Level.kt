package MovingEnergy

import MovingEnergy.Trigger.TriggerList
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.Disposable
import ktx.app.clearScreen
import ktx.box2d.createWorld
import ktx.box2d.earthGravity


class Level(val map: TiledMap, val music: Music) : Disposable {
    val renderer = OrthogonalTiledMapRenderer(map)
    val player: Player
    val world = createWorld(earthGravity)
    val mapWidth = map.properties.get("width", Int::class.java)
    val mapHeight = map.properties.get("height", Int::class.java)
    val camera: OrthographicCamera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    var trigger: TriggerList? = null
    var color: Color? = null

    init {
        camera.setToOrtho(false)
        val x = map.properties.get("playerX", Int::class.java).toFloat()
        val y = map.properties.get("playerY", Int::class.java).toFloat()
        if (map.properties.containsKey("bg")) {
            color = Color(map.properties.get("bg", Int::class.java))
        }
        player = Player(x * 32, (mapHeight - y) * 32, this)
        world.setContactListener(player)


        for (layer in map.layers.getByType(TiledMapTileLayer::class.java)) {
            repeat(layer.width) {
                val i = it
                repeat(layer.height) {
                    if (layer.getCell(i, it) != null)
                        world.createTileBody(layer.getCell(i, it).tile, i, it)
                }
            }
        }

        map.layers.flatMap { it.objects }.forEach { world.createTrigger(it) }

        music.play()
    }

    fun update(delta: Float) {
        if (color != null)
            clearScreen(color!!.r, color!!.g, color!!.b, color!!.a)

        camera.position.set(player.getX() + (player.getWidth() / 2), player.getY() + (player.getHeight() / 2), 0F)

        if (mapWidth * 32 < camera.viewportWidth)
            camera.position.x = mapWidth * 32 / 2F
        else if (camera.position.x - camera.viewportWidth / 2 <= 0)
            camera.position.x = camera.viewportWidth / 2
        else if (camera.position.x + camera.viewportWidth / 2 >= mapWidth * 32)
            camera.position.x = mapWidth * 32 - camera.viewportWidth / 2

        if (mapHeight * 32 < camera.viewportHeight)
            camera.position.y = mapHeight * 32 / 2F
        else if (camera.position.y - camera.viewportHeight / 2 <= 0)
            camera.position.y = camera.viewportHeight / 2
        else if (camera.position.y + camera.viewportHeight / 2 >= mapHeight * 32)
            camera.position.y = mapHeight * 32 - camera.viewportHeight / 2

        camera.update()
        renderer.setView(camera)

        world.step(delta, 6, 2)
        player.update()

        if (player.getX() < 0 || player.getX() > mapWidth * 32 || player.getY() < 0 || player.getY() > mapHeight * 32)
            player.kill()

        renderer.render()

        trigger?.update(this)

        renderer.batch.begin()
        player.draw(renderer.batch)
        renderer.batch.end()
    }

    override fun dispose() {
        map.dispose()
        renderer.dispose()
        world.dispose()
    }
}
