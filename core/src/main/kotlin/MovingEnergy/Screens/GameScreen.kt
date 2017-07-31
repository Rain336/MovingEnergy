package MovingEnergy.Screens

import MovingEnergy.Assets
import MovingEnergy.Level
import ktx.app.KtxScreen

class GameScreen : KtxScreen {
    companion object {
        var deaths = 0
    }

    lateinit var level: Level
    private var first = true

    override fun show() {
        level = Level(Assets.level1)
    }

    override fun render(delta: Float) {
        level.update(delta)
    }

    override fun dispose() {
        level.dispose()
    }
}
