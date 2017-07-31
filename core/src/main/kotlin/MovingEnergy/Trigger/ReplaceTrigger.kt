package MovingEnergy.Trigger

import MovingEnergy.Level
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Vector2

class ReplaceTrigger(val tid: Int, val elements: ArrayList<Vector2>) : ITrigger {

    override fun update(level: Level): Boolean {
        val target = level.map.tileSets.getTile(tid)
        for (layer in level.map.layers.getByType(TiledMapTileLayer::class.java)) {
            for (i in elements) {
                layer.getCell(i.x.toInt(), i.y.toInt()).tile = target
            }
        }
        return true
    }
}
