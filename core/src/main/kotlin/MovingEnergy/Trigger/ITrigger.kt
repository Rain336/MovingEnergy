package MovingEnergy.Trigger

import MovingEnergy.Level


interface ITrigger {
    fun update(level: Level): Boolean
}
