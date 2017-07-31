package MovingEnergy

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.maps.tiled.TiledMap
import ktx.assets.getValue
import ktx.assets.load

object Assets {
    val idle0 by MovingEnergy.manager.load<Texture>("player/idle0.png")
    val idle1 by MovingEnergy.manager.load<Texture>("player/idle1.png")
    val move0 by MovingEnergy.manager.load<Texture>("player/move0.png")
    val move1 by MovingEnergy.manager.load<Texture>("player/move1.png")
    val blitz by MovingEnergy.manager.load<Texture>("blitz.png")
    val fira by MovingEnergy.manager.load<BitmapFont>("firamono.fnt")

    val level1 by MovingEnergy.manager.load<TiledMap>("level1/level1.tmx")
    val level2 by MovingEnergy.manager.load<TiledMap>("level2/level2.tmx")

    val jump by MovingEnergy.manager.load<Sound>("jump.wav")
    val music1 by MovingEnergy.manager.load<Music>("level1/level1.mp3")
    val music2 by MovingEnergy.manager.load<Music>("level2/level2.mp3")
}
