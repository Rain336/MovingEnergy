package MovingEnergy

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import ktx.assets.getValue
import ktx.assets.load

object Assets {
    val idle0: Texture by MovingEnergy.manager.load<Texture>("player/idle0.png")
    val idle1: Texture by MovingEnergy.manager.load<Texture>("player/idle1.png")
    val move0: Texture by MovingEnergy.manager.load<Texture>("player/move0.png")
    val move1: Texture by MovingEnergy.manager.load<Texture>("player/move1.png")
    val blitz: Texture by MovingEnergy.manager.load<Texture>("blitz.png")
    val fira: BitmapFont by MovingEnergy.manager.load<BitmapFont>("firamono.fnt")
}
