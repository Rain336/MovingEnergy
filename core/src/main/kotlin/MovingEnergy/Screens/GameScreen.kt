package MovingEnergy.Screens

import MovingEnergy.Player
import MovingEnergy.createTileBody
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import ktx.app.KtxScreen
import ktx.box2d.createWorld
import ktx.box2d.earthGravity

class GameScreen : KtxScreen {
    lateinit var map: TiledMap
    lateinit var renderer: OrthogonalTiledMapRenderer
    lateinit var player: Player
    lateinit var world: World
    lateinit var debug: Box2DDebugRenderer
    var mapWidth: Int = 0
    var mapHeight: Int = 0
    val camera: OrthographicCamera = OrthographicCamera()

    override fun show() {
        map = TmxMapLoader().load("debug/debug.tmx")
        renderer = OrthogonalTiledMapRenderer(map)
        world = createWorld(earthGravity)
        debug = Box2DDebugRenderer()
        mapWidth = map.properties.get("width", Int::class.java)
        mapHeight = map.properties.get("height", Int::class.java)

        camera.setToOrtho(false)
        val x = map.properties.get("playerX", Int::class.java).toFloat()
        val y = map.properties.get("playerY", Int::class.java).toFloat() - 3
        player = Player(x * 32, (mapHeight - y) * 32, world)
        world.setContactListener(player)

        val layer = map.layers[0] as TiledMapTileLayer
        repeat(layer.width) {
            val i = it
            repeat(layer.height) {
                if (layer.getCell(i, it) != null)
                    world.createTileBody(layer.getCell(i, it).tile, i, it)
            }
        }
    }

    override fun render(delta: Float) {
        camera.position.set(player.getX() + (player.getWidth() / 2), player.getY() + (player.getHeight() / 2), 0F)

        if(mapWidth * 32 < camera.viewportWidth)
            camera.position.x = mapWidth * 32 / 2F
        else if (camera.position.x - camera.viewportWidth / 2 <= 0)
            camera.position.x = camera.viewportWidth / 2
        else if(camera.position.x + camera.viewportWidth / 2 >= mapWidth * 32)
            camera.position.x = mapWidth * 32 - camera.viewportWidth / 2

        if(mapHeight * 32 < camera.viewportHeight)
            camera.position.y = mapHeight * 32 / 2F
        else if(camera.position.y - camera.viewportHeight / 2 <= 0)
            camera.position.y = camera.viewportHeight / 2
        else if(camera.position.y + camera.viewportHeight / 2 >= mapHeight * 32)
            camera.position.y = mapHeight * 32 - camera.viewportHeight / 2

        camera.update()
        renderer.setView(camera)

        world.step(delta, 6, 2)
        player.update()

        if(player.getX() < 0 || player.getX() > mapWidth * 32 || player.getY() < 0 || player.getY() > mapHeight * 32)
            player.kill()

        renderer.render()

        renderer.batch.begin()
        player.draw(renderer.batch)
        renderer.batch.end()

        debug.render(world, camera.combined)
    }

    override fun resize(width: Int, height: Int) {
        camera.viewportWidth = width.toFloat()
        camera.viewportHeight = height.toFloat()
        camera.update()
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        map.dispose()
        renderer.dispose()
        world.dispose()
        debug.dispose()
    }
}
