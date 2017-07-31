package MovingEnergy.Trigger

import MovingEnergy.Assets
import MovingEnergy.Level

class TyperTrigger(val text: String) : ITrigger {
    private val buffer = StringBuffer()
    private var index = 0
    private var timer = 0

    override fun update(level: Level): Boolean {
        timer++

        if(timer == 15) {
            buffer.append(text[index])
            index++
            timer = 0
        }

        val batch = level.renderer.batch
        batch.begin()
        Assets.fira.draw(batch, buffer, level.player.getX(), level.player.getY() + 125F)
        batch.end()

        return index == text.length
    }
}
