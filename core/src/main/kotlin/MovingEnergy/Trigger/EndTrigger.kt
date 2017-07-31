package MovingEnergy.Trigger

import MovingEnergy.Assets
import MovingEnergy.Screens.EndGameScreen
import MovingEnergy.Level


class EndTrigger : ITrigger {
    private var timer = 0

    override fun update(level: Level): Boolean {
        timer++
        if(timer == 30) {
            Assets.music1.stop()
            MovingEnergy.MovingEnergy.setScreen<EndGameScreen>()
            return true
        }
        return false
    }
}
