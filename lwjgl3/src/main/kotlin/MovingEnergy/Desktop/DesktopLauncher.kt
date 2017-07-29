package MovingEnergy.Desktop

import MovingEnergy.MovingEnergy
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

fun main(args: Array<String>) {
    val config = Lwjgl3ApplicationConfiguration()
    config.setWindowedMode(1280, 720)
    config.setTitle("MovingEnergy")
    Lwjgl3Application(MovingEnergy, config)
}
