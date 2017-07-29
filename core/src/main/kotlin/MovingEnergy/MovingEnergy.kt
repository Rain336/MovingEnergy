package MovingEnergy

import MovingEnergy.Screens.GameScreen
import MovingEnergy.Screens.LoadingScreen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.box2d.body

object MovingEnergy : KtxGame<KtxScreen>(LoadingScreen()) {
    val manager = AssetManager()

    override fun create() {
        manager.setLoader(TiledMap::class.java, TmxMapLoader(InternalFileHandleResolver()))
        addScreen(GameScreen())
        super.create()
    }

    override fun dispose() {
        super.dispose()
        manager.dispose()
    }
}

fun TiledMapTileLayer.getCellFromPixels(x: Int, y: Int): TiledMapTileLayer.Cell {
    return getCell(x / tileWidth.toInt(), y / tileHeight.toInt())
}

fun Float.toGridPos(): Float {
    return this / 32
}

fun Float.toPixelPos(): Float {
    return this * 32
}

fun World.createTileBody(tile: TiledMapTile, x: Int, y: Int) {
    body {
        type = BodyDef.BodyType.StaticBody
        box(32F, 32F, Vector2(x * 32F, y * 32F)) {
            density = if (tile.properties.containsKey("density")) tile.properties.get("density", Float::class.java) else 2F
            friction = if (tile.properties.containsKey("friction")) tile.properties.get("friction", Float::class.java) else 0.2F
            restitution = if (tile.properties.containsKey("restitution")) tile.properties.get("restitution", Float::class.java) else 0F
        }
    }
}
