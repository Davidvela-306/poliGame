package io.github.davidvela306.poliGame

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport

class Core : ApplicationAdapter() {
    private lateinit var backgroundTexture: Texture
    private lateinit var perritoTexture: Texture
    private lateinit var croquetaTexture: Texture
    private lateinit var endGameTexture: Texture
    private lateinit var croquetaSound: Sound
    private lateinit var loseSound: Sound
    private lateinit var music: Music
    private lateinit var spriteBatch: SpriteBatch
    private lateinit var viewport: FitViewport
    private lateinit var perritoSprite: Sprite
    private lateinit var touchPos: Vector2
    private lateinit var croquetaSprites: Array<Sprite>
    private lateinit var perritoRectangle: Rectangle
    private lateinit var croquetaRectangle: Rectangle

    private var dropTimer = 0f
    private var gameTime = 0f
    private var baseSpeed = 2f
    private var isGameOver = false

    override fun create() {
        // Carga de recursos
        backgroundTexture = Texture("background.png")
        perritoTexture = Texture("poliperro.png")
        croquetaTexture = Texture("croqueta.png")
        endGameTexture = Texture("endGame.png")
        croquetaSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"))
        loseSound = Gdx.audio.newSound(Gdx.files.internal("lose.mp3"))
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"))

        // Inicialización de objetos
        spriteBatch = SpriteBatch()
        viewport = FitViewport(8f, 5f)
        perritoSprite = Sprite(perritoTexture).apply {
            setSize(1.4f, 1.7f)
        }
        touchPos = Vector2()
        croquetaSprites = Array()
        perritoRectangle = Rectangle()
        croquetaRectangle = Rectangle()

        // Configuración de la música
        music.isLooping = true
        music.volume = 0.5f
        music.play()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun render() {
        if (!isGameOver) {
            input()
            logic()
            draw()
        } else {
            drawGameOver()
        }
    }

    private fun input() {
        val speed = 4f
        val delta = Gdx.graphics.deltaTime

        when {
            Gdx.input.isKeyPressed(Input.Keys.RIGHT) -> perritoSprite.translateX(speed * delta)
            Gdx.input.isKeyPressed(Input.Keys.LEFT) -> perritoSprite.translateX(-speed * delta)
        }

        if (Gdx.input.isTouched) {
            touchPos.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
            viewport.unproject(touchPos)
            perritoSprite.setCenterX(touchPos.x)
        }
    }

    private fun logic() {
        val worldWidth = viewport.worldWidth
        val worldHeight = viewport.worldHeight
        val perritoWidth = perritoSprite.width
        val perritoHeight = perritoSprite.height

        // Actualizar tiempo de juego y velocidad
        val delta = Gdx.graphics.deltaTime
        gameTime += delta
        val currentSpeed = baseSpeed + (gameTime / 10f) // Aumenta la velocidad gradualmente

        // Evita que el perrito salga de la pantalla
        perritoSprite.x = MathUtils.clamp(perritoSprite.x, 0f, worldWidth - perritoWidth)

        perritoRectangle.set(perritoSprite.x, perritoSprite.y, perritoWidth, perritoHeight)

        for (i in croquetaSprites.size - 1 downTo 0) {
            val croquetaSprite = croquetaSprites[i]
            val croquetaWidth = croquetaSprite.width
            val croquetaHeight = croquetaSprite.height

            croquetaSprite.translateY(-currentSpeed * delta)
            croquetaRectangle.set(croquetaSprite.x, croquetaSprite.y, croquetaWidth, croquetaHeight)

            // Verificar si la croqueta toca el suelo
            if (croquetaSprite.y < 0f) {
                gameOver()
                return
            }

            if (perritoRectangle.overlaps(croquetaRectangle)) {
                croquetaSprites.removeIndex(i)
                croquetaSound.play()
            }
        }

        dropTimer += delta
        if (dropTimer > 1f) {
            dropTimer = 0f
            createCroqueta()
        }
    }

    private fun gameOver() {
        isGameOver = true
        music.stop()
        loseSound.play()
        croquetaSprites.clear()
    }

    private fun draw() {
        ScreenUtils.clear(Color.BLACK)
        viewport.apply()
        spriteBatch.projectionMatrix = viewport.camera.combined

        spriteBatch.begin()

        val worldWidth = viewport.worldWidth
        val worldHeight = viewport.worldHeight

        spriteBatch.draw(backgroundTexture, 0f, 0f, worldWidth, worldHeight)
        perritoSprite.draw(spriteBatch)

        for (croquetaSprite in croquetaSprites) {
            croquetaSprite.draw(spriteBatch)
        }

        spriteBatch.end()
    }

    private fun drawGameOver() {
        ScreenUtils.clear(Color.BLACK)
        viewport.apply()
        spriteBatch.projectionMatrix = viewport.camera.combined

        spriteBatch.begin()
        spriteBatch.draw(endGameTexture, 0f, 0f, viewport.worldWidth, viewport.worldHeight)
        spriteBatch.end()
    }

    private fun createCroqueta() {
        val croquetaWidth = 1f
        val croquetaHeight = 1f
        val worldWidth = viewport.worldWidth
        val worldHeight = viewport.worldHeight

        val croquetaSprite = Sprite(croquetaTexture).apply {
            setSize(croquetaWidth, croquetaHeight)
            setX(MathUtils.random(0f, worldWidth - croquetaWidth))
            setY(worldHeight)
        }
        croquetaSprites.add(croquetaSprite)
    }

    override fun dispose() {
        backgroundTexture.dispose()
        perritoTexture.dispose()
        croquetaTexture.dispose()
        endGameTexture.dispose()
        croquetaSound.dispose()
        loseSound.dispose()
        music.dispose()
        spriteBatch.dispose()
    }
}
