package MovingEnergy

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import ktx.box2d.body

class Player(var x: Float, var y: Float, world: World) {
    private var timer = 0L
    private var secondary = false
    var idle = true
    val body = world.body {
        box(32F, 64F, Vector2(x, y)) {
            density = 2F
        }
    }

    fun draw(batch: Batch) {
        x = body.position.x.toPixelPos()
        y = body.position.y.toPixelPos()

        timer++
        if (timer == 120L) {
            batch.draw(
                    if (idle)
                        if (!secondary) Assets.idle1 else Assets.idle0
                    else
                        if (!secondary) Assets.move0 else Assets.move1,
                    x, y)
            secondary = !secondary
            timer = 0
        }
    }
}
