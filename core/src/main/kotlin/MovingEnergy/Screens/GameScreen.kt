package MovingEnergy.Screens

import MovingEnergy.Assets
import MovingEnergy.Level
import ktx.app.KtxScreen

class GameScreen : KtxScreen {
    companion object {
        var deaths = 0
    }

    var level: Level? = null

    override fun show() {
        level = Level(Assets.level1, Assets.music1)
    }

    override fun render(delta: Float) {
        level?.update(delta)
    }

    override fun dispose() {
        level?.dispose()
    }
}
