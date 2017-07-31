package MovingEnergy

import MovingEnergy.Screens.GameScreen
import MovingEnergy.Screens.TutorialSceen
import MovingEnergy.Screens.LoadingScreen
import MovingEnergy.Screens.EndGameScreen
import MovingEnergy.Trigger.TriggerList
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.box2d.body

object MovingEnergy : KtxGame<KtxScreen>(LoadingScreen()) {
    val manager = AssetManager()

    override fun create() {
        manager.setLoader(TiledMap::class.java, TmxMapLoader(InternalFileHandleResolver()))
        addScreen(TutorialSceen())
        addScreen(GameScreen())
        addScreen(EndGameScreen())
        super.create()
    }

    override fun dispose() {
        super.dispose()
        manager.dispose()
    }
}

const val physicsScale = 32

fun World.createTrigger(obj: MapObject) {
    val list = TriggerList(obj.properties)
    list.body = body {
        box((obj.properties.get("width", Int::class.java).toFloat() + 16F) / physicsScale,
                (obj.properties.get("height", Int::class.java).toFloat() + 16F) / physicsScale,
                Vector2((obj.properties.get("x", Int::class.java).toFloat() + 16F) / physicsScale,
                        (obj.properties.get("y", Int::class.java).toFloat() + 16F) / physicsScale)) {
            isSensor = true
        }
        userData = list
    }
}

fun World.createTileBody(tile: TiledMapTile, x: Int, y: Int) {
    if (tile.properties.containsKey("trigger")) return
    body {
        box(1F, 1F, Vector2((x * 32F + 16F) / physicsScale, (y * 32F + 16F) / physicsScale)) {
            density = if (tile.properties.containsKey("density")) tile.properties.get("density", Float::class.java) else 0F
            friction = if (tile.properties.containsKey("friction")) tile.properties.get("friction", Float::class.java) else 0.2F
            restitution = if (tile.properties.containsKey("restitution")) tile.properties.get("restitution", Float::class.java) else 0F
        }
        userData = Vector2(x.toFloat(), y.toFloat())
    }
}
