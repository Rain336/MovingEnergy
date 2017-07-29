package MovingEnergy.Screens

import MovingEnergy.MovingEnergy
import ktx.app.KtxScreen
import MovingEnergy.Assets
import com.badlogic.gdx.graphics.Color

class LoadingScreen : KtxScreen {

    override fun show() {
        Assets
    }

    override fun render(delta: Float) {
        if(MovingEnergy.manager.update()) {
            Assets.fira.color = Color.WHITE
            MovingEnergy.setScreen<GameScreen>()
        }
    }
}
