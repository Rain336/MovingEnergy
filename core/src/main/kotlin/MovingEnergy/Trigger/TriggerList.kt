package MovingEnergy.Trigger

import MovingEnergy.Level
import com.badlogic.gdx.maps.MapProperties
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body


class TriggerList(properties: MapProperties) : ArrayList<ITrigger>() {
    private var index = 0
    lateinit var body: Body

    init {
        if (properties.containsKey("type")) {
            val input = properties.get("type", String::class.java)
            for (i in input.split('|'))
                add(TyperTrigger(i))
        }

        if (properties.containsKey("destroy")) {
            val elements = ArrayList<Vector2>()
            val split = properties.get("destroy", String::class.java).split('|')
            for (i in 0..split.size - 1 step 2) {
                elements.add(Vector2(split[i].toFloat(), split[i + 1].toFloat()))
            }
            add(DestroyTrigger(elements))
        }

        for (i in properties.keys) {
            if (i.startsWith("replace")) {
                val elements = ArrayList<Vector2>()
                val split = properties.get(i, String::class.java).split('|')
                for (i in 0..split.size - 1 step 2) {
                    elements.add(Vector2(split[i].toFloat(), split[i + 1].toFloat()))
                }
                add(ReplaceTrigger(i.replace("replace", "").toInt(), elements))
            }
        }
    }

    fun update(level: Level) {
        if (index >= size) {
            level.trigger = null
            body.userData = null
            return
        }

        if (this[index].update(level)) {
            index++
        }
    }
}
