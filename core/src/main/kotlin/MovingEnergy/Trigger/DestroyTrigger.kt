package MovingEnergy.Trigger

import MovingEnergy.Level
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.utils.Array

class DestroyTrigger(val elements: ArrayList<Vector2>) : ITrigger {
    override fun update(level: Level): Boolean {
        val empty = level.map.tileSets.getTile(13)
        for (layer in level.map.layers.getByType(TiledMapTileLayer::class.java)) {
            for (i in elements) {
                layer.getCell(i.x.toInt(), level.mapHeight - i.y.toInt())?.tile = empty
            }
        }

        val array = Array<Body>(level.world.bodyCount)
        level.world.getBodies(array)
        array.filter { it.userData is Vector2 }.forEach {
            val vec = it.userData as Vector2
            val body = it
            elements.filter { it.x == vec.x && level.mapHeight - it.y == vec.y }.forEach { level.world.destroyBody(body) }
        }

        return true
    }
}
