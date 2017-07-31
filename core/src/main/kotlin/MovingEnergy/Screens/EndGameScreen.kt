package MovingEnergy.Screens

import MovingEnergy.Assets
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import ktx.app.KtxScreen
import ktx.collections.gdxArrayOf
import java.util.*


class EndGameScreen : KtxScreen {
    companion object {
        val random = Random()
        val dragon = gdxArrayOf(
                gdxArrayOf(
                        "Did you know! A dragon matures a the age of 140 years",
                        "That means a human can't even surpass the youth of a dragon.",
                        ""
                )
        )
        val lines = gdxArrayOf(
                "You went down the staircase.",
                "But the last stair. IT WAS WET",
                "You slipped and fell onto the steel stairs.",
                "You didn't die, but you can't move",
                "And your Energy slowly runs out.",
                "",
                "You did it!",
                "The game is over. Short heh ;)",
                "Anyways! You died {DEATHS} times.",
                "Cool!",
                "And now a Random Dragon Did-you-know:"
        )
    }

    private val table = Table()
    private val stage = Stage()
    private var index = 0
    private var timer = 0
    private var done = false
    private var first = true
    private var text = lines
    private val quote = dragon[random.nextInt(dragon.size)]
    private lateinit var style: Label.LabelStyle

    override fun show() {
        table.setBounds(0F, 0F, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        stage.addActor(table)
        style = Label.LabelStyle(Assets.fira, Color.WHITE)
    }

    override fun render(delta: Float) {
        if (done && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (first) {
                table.reset()
                text = quote
                index = 0
                timer = 0
                first = false
                done = false
            } else {
                Gdx.app.exit()
            }
        }

        timer++
        if (timer % 60 == 0)
            update()

        stage.act(delta)
        stage.draw()
    }

    fun update() {
        if (index >= text.size) {
            if (!done)
                table.add(Label("Press SPACE to continue...", style)).pad(5F).row()
            done = true
            return
        }
        table.add(Label(text[index].replace("{DEATHS}", GameScreen.deaths.toString()), style)).pad(5F).row()
        index++
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height)
        table.invalidateHierarchy()
    }

    override fun dispose() {
        stage.dispose()
    }
}
