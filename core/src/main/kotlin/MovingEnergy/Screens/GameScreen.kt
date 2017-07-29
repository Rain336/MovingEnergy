package MovingEnergy.Screens

import MovingEnergy.Player
import MovingEnergy.createTileBody
import MovingEnergy.toPixelPos
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import ktx.app.KtxScreen
import ktx.box2d.createWorld

class GameScreen : KtxScreen {
    lateinit var map: TiledMap
    lateinit var renderer: OrthogonalTiledMapRenderer
    lateinit var player: Player
    lateinit var world: World
    lateinit var debug: Box2DDebugRenderer
    val camera: OrthographicCamera = OrthographicCamera()

    override fun show() {
        map = TmxMapLoader().load("debug/debug.tmx")
        renderer = OrthogonalTiledMapRenderer(map)
        renderer.setView(camera)
        world = createWorld()
        debug = Box2DDebugRenderer()

        val x = map.properties.get("playerX", 0, Int::class.java).toFloat()
        val y = map.properties.get("playerY", 0, Int::class.java).toFloat()
        player = Player(x.toPixelPos(), y.toPixelPos(), world)

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
        Gdx.gl20.glClearColor(0F, 0F, 0F, 1F)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        world.step(1F / 60F, 6, 2)

        camera.lookAt(player.x, player.y, 0F)
        camera.update()
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
