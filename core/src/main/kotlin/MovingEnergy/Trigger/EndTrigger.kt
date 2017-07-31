package MovingEnergy.Trigger

import MovingEnergy.Level
import MovingEnergy.MovingEnergy
import MovingEnergy.Screens.EndGameScreen


class EndTrigger : ITrigger {
    private var timer = 0

    override fun update(level: Level): Boolean {
        timer++
        if (timer == 30) {
            level.music.stop()
            MovingEnergy.setScreen<EndGameScreen>()
            return true
        }
        return false
    }
}
