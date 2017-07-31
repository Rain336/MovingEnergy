package MovingEnergy.Trigger

import MovingEnergy.Assets
import MovingEnergy.Level
import MovingEnergy.MovingEnergy
import MovingEnergy.Screens.GameScreen

class SwitchLevelTrigger(val id: Int) : ITrigger {
    companion object {
        fun switchTo(id: Int) {
            val screen = MovingEnergy.getScreen<GameScreen>()
            screen.level?.dispose()
            when (id) {
                1 -> screen.level = Level(Assets.level1, Assets.music1)
                2 -> screen.level = Level(Assets.level2, Assets.music2)
            }
        }
    }

    private var timer = 0

    override fun update(level: Level): Boolean {
        timer++
        if (timer == 30) {
            level.music.stop()
            switchTo(id)
            return true
        }
        return false
    }
}
