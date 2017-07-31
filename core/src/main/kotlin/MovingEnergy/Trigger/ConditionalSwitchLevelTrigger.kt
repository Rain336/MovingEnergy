package MovingEnergy.Trigger

import MovingEnergy.Level
import MovingEnergy.MovingEnergy
import MovingEnergy.Screens.EndGameScreen
import MovingEnergy.Screens.GameScreen


class ConditionalSwitchLevelTrigger(val deaths: Int, val id: Int, val alt: Int) : ITrigger {
    private var timer = 0

    override fun update(level: Level): Boolean {
        timer++
        if (timer == 30) {
            level.music.stop()
            if (GameScreen.deaths >= deaths) {
                if (alt == 0)
                    MovingEnergy.setScreen<EndGameScreen>()
                else
                    SwitchLevelTrigger.switchTo(alt)
            } else {
                if (id == 0)
                    MovingEnergy.setScreen<EndGameScreen>()
                else
                    SwitchLevelTrigger.switchTo(id)
            }
            return true
        }
        return false
    }
}
