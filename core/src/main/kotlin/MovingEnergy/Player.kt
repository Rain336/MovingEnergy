package MovingEnergy

import MovingEnergy.Screens.GameScreen
import MovingEnergy.Trigger.TriggerList
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ktx.box2d.body

class Player(private val x: Float, private val y: Float, val level: Level) : ContactListener {
    private var timer = 0
    private var jump = 0
    private var secondary = false
    private var texture: Texture = Assets.idle0
    private var onGround = 0
    private var speed = 0F
    var energy = 100
    var idle = true
    val body = level.world.body {
        type = BodyDef.BodyType.DynamicBody
        fixedRotation = true
        box(1F, 2F, Vector2((x + 16F) / physicsScale, (y + 32F) / physicsScale)) {}
    }

    fun getX(): Float {
        return x + body.position.x * physicsScale
    }

    fun getY(): Float {
        return y + body.position.y * physicsScale
    }

    fun getHeight() = texture.height
    fun getWidth() = texture.width

    fun update() {
        idle = body.linearVelocity.epsilonEquals(0F, 0F, 0.00001F)

        if (jump > 0)
            jump--
        timer++
        if (timer % 60 == 0)
            energy--

        if (!idle && timer % 30 == 0) {
            energy++
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (speed >= 0) speed = -1F
            else speed = Math.max(speed - 1F, -5F)
            body.applyForceToCenter(speed, 0F, true)
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (speed <= 0) speed = 1F
            else speed = Math.min(speed + 1F, 5F)
            body.applyForceToCenter(speed, 0F, true)
        }

        if (onGround > 0 && jump == 0 && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            body.applyLinearImpulse(Vector2(0F, body.mass * 10F), body.worldCenter, true)
            energy -= 10
            jump = 15
        }

        if (energy <= 0) {
            kill()
        } else if (energy > 100)
            energy = 100
    }

    fun draw(batch: Batch) {
        batch.draw(texture, getX(), getY())
        batch.draw(Assets.blitz, getX() - 20, getY() + 75)
        Assets.fira.draw(batch, energy.toString(), getX() + 20, getY() + 100)

        if (timer == 120) {
            texture =
                    if (idle)
                        if (!secondary) Assets.idle1 else Assets.idle0
                    else
                        if (!secondary) Assets.move0 else Assets.move1
            secondary = !secondary
            timer = 0
        }
    }

    fun kill() {
        energy = 100
        speed = 0F
        onGround = 0
        body.angularVelocity = 0F
        body.linearVelocity = Vector2.Zero
        body.setTransform(0F, 0F, 0F)
        GameScreen.deaths++
    }

    override fun beginContact(contact: Contact) {
        if (contact.fixtureA.body == body) {
            if (contact.fixtureB.body.userData is TriggerList)
                level.trigger = contact.fixtureB.body.userData as TriggerList
            else if (contact.fixtureB.body.userData is Vector2 && (contact.fixtureB.body.userData as Vector2).y < getY())
                onGround++
        } else if (contact.fixtureB.body == body) {
            if (contact.fixtureA.body.userData is TriggerList)
                level.trigger = contact.fixtureA.body.userData as TriggerList
            else if (contact.fixtureB.body.userData is Vector2 && (contact.fixtureA.body.userData as Vector2).y < getY())
                onGround++
        }
    }

    override fun endContact(contact: Contact) {
        if (contact.fixtureA.body == body) {
            if (!contact.fixtureB.isSensor && (contact.fixtureB.body.userData as Vector2).y < getY())
                onGround = Math.max(onGround - 1, 0)
        } else if (contact.fixtureB.body == body) {
            if (!contact.fixtureA.isSensor && (contact.fixtureA.body.userData as Vector2).y < getY())
                onGround = Math.max(onGround - 1, 0)
        }
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold) {}

    override fun postSolve(contact: Contact, impulse: ContactImpulse) {}
}
