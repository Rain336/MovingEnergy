package MovingEnergy.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import ktx.app.KtxScreen


class GameScreen : KtxScreen {
    lateinit var map: TiledMap
    lateinit var renderer: OrthogonalTiledMapRenderer
    val camera: OrthographicCamera = OrthographicCamera()

    override fun show() {
        map = TmxMapLoader().load("maps/debug.tmx")
        renderer = OrthogonalTiledMapRenderer(map)
        renderer.setView(camera)
    }

    override fun render(delta: Float) {
        Gdx.gl20.glClearColor(0F, 0F, 0F, 1F)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.render()
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
    }
}
