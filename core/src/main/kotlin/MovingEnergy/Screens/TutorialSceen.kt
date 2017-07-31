package MovingEnergy.Screens

import MovingEnergy.Assets
import MovingEnergy.MovingEnergy
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import ktx.app.KtxScreen
import ktx.collections.gdxArrayOf


class TutorialSceen : KtxScreen {
    companion object {
        val lines0 = gdxArrayOf(
                "Okay. This game is simple.",
                "You can move with W,A,S,D or the arrow keys.",
                "You can jump with space.",
                "You loose energy all the time",
                "but gain energy while moving.",
                "Jumping cost 10 Energy.",
                "OK?",
                "Press SPACE to continue..."
        )
        val lines1 = gdxArrayOf(
                "You are a human with high-tech pants. YAY",
                "You require constant energy to charge your hart (and your phone).",
                "Your fancy pants generate 2 Energy per second. Enough for your needs.",
                "But here comes the catch. They only generate it when you are moving.",
                "So you are going to your nearest lab to install a passive generator.",
                "Much Luck!",
                "Press SPACE to continue..."
        )
    }

    private val table = Table()
    private val stage = Stage()
    private var index = 0
    private var timer = 0
    private var text = lines0
    private var first = true
    private var done = false
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
                text = lines1
                index = 0
                timer = 0
                first = false
                done = false
            } else {
                MovingEnergy.setScreen<GameScreen>()
                return
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
            done = true
            return
        }
        table.add(Label(text[index], style)).pad(5F).row()
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
