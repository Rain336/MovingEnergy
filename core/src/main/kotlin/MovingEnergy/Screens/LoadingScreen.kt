package MovingEnergy.Screens

import MovingEnergy.MovingEnergy
import ktx.app.KtxScreen
import MovingEnergy.Assets

class LoadingScreen : KtxScreen {

    override fun show() {
        Assets
    }

    override fun render(delta: Float) {
        if(MovingEnergy.manager.update()) {
            MovingEnergy.setScreen<GameScreen>()
        }
    }
}
