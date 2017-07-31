package MovingEnergy.Screens

import MovingEnergy.Assets
import MovingEnergy.MovingEnergy
import com.badlogic.gdx.graphics.Color
import ktx.app.KtxScreen

class LoadingScreen : KtxScreen {
    override fun show() {
        Assets
    }

    override fun render(delta: Float) {
        if (MovingEnergy.manager.update()) {
            Assets.fira.color = Color.WHITE
            MovingEnergy.setScreen<TutorialSceen>()
        }
    }
}
