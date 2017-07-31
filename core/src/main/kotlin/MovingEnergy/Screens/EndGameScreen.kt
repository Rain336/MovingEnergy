package MovingEnergy.Screens

import MovingEnergy.Assets
import MovingEnergy.MovingEnergy
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import ktx.app.KtxScreen
import ktx.collections.gdxArrayOf


class EndGameScreen : KtxScreen {
    companion object {
        /*********************************************/
        /**
         * I'm sorry, but including this would be too Random.
         * Even thou I <3 Dragons.
         */
        val dragon = gdxArrayOf(
                gdxArrayOf(
                        "Did you know! A dragon matures a the age of 140 years",
                        "That means a human can't even surpass the youth of a dragon.",
                        "How sad :(",
                        ""
                )
        )
        /*************************************************/
        val lines1 = gdxArrayOf(
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
                "P.S.: The song was 'Retrochillwave' by zCore",
                "and is listened under the CC BY-NC-SA 3.0 License",
                "Press SPACE to continue..."
        )
        val lines2 = gdxArrayOf(
                "You obtained an ancient device.",
                "It can generate unlimited power",
                "out of nothing.",
                "It is probably worth a fortune, BUT",
                "you need to charge your phone!",
                "",
                "You did it!",
                "The game is over. Short heh ;)",
                "Anyways! You died {DEATHS} times.",
                "Cool!",
                "P.S.: The songs are 'Retrochillwave' and 'Not Far Away' by zCore",
                "and are listened under the CC BY-NC-SA 3.0 License",
                "Press SPACE to continue..."
        )
    }

    private val table = Table()
    private val stage = Stage()
    private var index = 0
    private var timer = 0
    private var done = false
    private lateinit var text: Array<String>
    private lateinit var style: Label.LabelStyle

    override fun show() {
        table.setBounds(0F, 0F, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        stage.addActor(table)
        style = Label.LabelStyle(Assets.fira, Color.WHITE)
        text = if (MovingEnergy.getScreen<GameScreen>().level!!.map == Assets.level1) lines1 else lines2
    }

    override fun render(delta: Float) {
        if (done && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            Gdx.app.exit()
        }

        timer++
        if (timer % 60 == 0)
            update()

        stage.act(delta)
        stage.draw()
    }

    fun update() {
        if (index >= text.size) {
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
