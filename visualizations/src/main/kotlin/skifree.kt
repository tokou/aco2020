import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.js.h2
import kotlinx.html.js.p
import org.w3c.dom.*
import org.w3c.dom.events.KeyboardEvent
import kotlin.math.roundToInt

val input = """
    ..##.......
    #...#...#..
    .#....#..#.
    ..#.#...#.#
    .#...##..#.
    ..#.##.....
    .#.#.#....#
    .#........#
    #.##...#...
    #...##....#
    .#..#...#.#
""".trimIndent()

fun showSkiFree() {
    document.getElementById("content")?.let {
        it.innerHTML = ""
        it.append { h2 { +"--- SkiFree ---" } }
        it.appendChild(Graphics.canvas)
        it.append { p { +"Press space to pause or restart"  } }
    }
    Loop.start()
}

object Params {
    const val fpsLimit = 60
    const val framesPerStep = 30
    val unit = 28 to 32
    val size = 20 to 15
    const val fallPercent = 0.2
    const val monsterDelay = 5
}

object State {
    var run = true
    var step = 0
    var treesHit = 0
    private var lastUpdate = 0
    val progress get() = lastUpdate.toDouble() / Params.framesPerStep

    fun update() {
        if (lastUpdate < Params.framesPerStep) {
            lastUpdate += 1
            return
        }
        lastUpdate = 0
        step += 1
        treesHit += if (Game.skiHit()) 1 else 0
        if (Game.monsterEat()) run = false
    }

    fun reset() {
        step = 0
        treesHit = 0
        lastUpdate = 0
    }
}

object Game {
    private val origin = 6 to 5
    private val slope = 3 to 1
    private val forest = Forest.parse(input)

    fun treePositions() = (0..Params.size.first + slope.first).flatMap { i -> (0..Params.size.second + slope.second).map { j ->
        val position = i to j
        position to position.treeCoord(State.step)
    } }.filter {
        forest.trees.contains(it.second)
    }.map {
        it.first.treePosition(State.progress)
    }

    fun skiPosition() = Params.unit * origin.toDouble()

    fun skiHit() = forest.trees.contains((State.step * slope).floorMod(forest.size)) && State.progress < Params.fallPercent

    fun monsterPosition(): Pair<Double, Double> {
        val x = forest.size.second - State.step - Params.monsterDelay - State.progress
        val a = slope.first to -slope.second
        val b = origin + Params.monsterDelay * a
        return Params.unit * if (x > 0) b.toDouble() else x * a + b
    }

    fun monsterEat() = monsterPosition() == skiPosition()

    private fun Pair<Int, Int>.treeCoord(step: Int) = (this - origin + step * slope).floorMod(forest.size)

    private fun Pair<Int, Int>.treePosition(progress: Double) = Params.unit * (this - progress * slope)
}

object Loop {

    private var frames = 0
    private var lastRender = 0.0

    init {
        window.addEventListener("keydown", {
            if ((it as KeyboardEvent).code != "Space") return@addEventListener
            if (Game.monsterEat()) State.reset()
            State.run = !State.run
            if (State.run) window.requestAnimationFrame(::loop)
        }, false)
    }

    private fun loop(timestamp: Double) {
        val deltaT = timestamp - lastRender
        val fpsInterval = 1000.0 / Params.fpsLimit
        val currentFps = (1000 / deltaT).roundToInt()

        if (deltaT > fpsInterval) {
            frames += 1
            State.update()
            Graphics.render(currentFps)
            lastRender = timestamp - (deltaT % fpsInterval)
        }

        if (State.run) window.requestAnimationFrame(::loop)
    }

    fun start() {
        if (State.run) window.requestAnimationFrame(::loop)
    }
}

object Images {

    var treeReady = false
    val treeImage = Image().apply {
        onload = { also { treeReady = true } }
        src = "tree.png"
    }

    var skiReady = false
    val skiImage = Image().apply {
        onload = { also { skiReady = true } }
        src = "ski.png"
    }

    var skiFallReady = false
    val skiFallImage = Image().apply {
        onload = { also { skiFallReady = true } }
        src = "ski-fall.png"
    }

    var monsterReady = false
    val monsterImage = Image().apply {
        onload = { also { monsterReady = true } }
        src = "monster.png"
    }

    var monsterEatReady = false
    val monsterEatImage = Image().apply {
        onload = { also { monsterEatReady = true } }
        src = "monster-eat.png"
    }

}

object Graphics {

    val canvas = (document.createElement("canvas") as HTMLCanvasElement).apply {
        width = Params.size.first * Params.unit.first
        height = Params.size.second * Params.unit.second
    }
    private val context = canvas.getContext("2d") as CanvasRenderingContext2D

    fun render(fps: Int) { with(context) {
        clear()
        drawTrees()
        drawSki()
        drawMonster()
        drawScore()
        drawGrid()
        drawDebug(fps)
    } }

    private fun CanvasRenderingContext2D.clear() {
        clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
        fillStyle = "rgb(240, 240, 240)"
        fillRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
    }

    private fun CanvasRenderingContext2D.drawDebug(fps: Int) {
        fillStyle = "rgb(50, 50, 50)"
        font = "12px Arial"
        textAlign = CanvasTextAlign.LEFT
        textBaseline = CanvasTextBaseline.TOP
        fillText("$fps", 4.0, 4.0)
    }

    private fun CanvasRenderingContext2D.drawScore() {
        fillStyle = "rgb(50, 50, 50)"
        font = "24px Helvetica"
        textAlign = CanvasTextAlign.LEFT
        textBaseline = CanvasTextBaseline.TOP
        fillText("Trees hit: ${State.treesHit}", Params.unit.first.toDouble(), Params.unit.second.toDouble())
    }

    private fun CanvasRenderingContext2D.drawGrid() {
        strokeStyle = "rgb(50, 50, 50)"
        strokeRect(0.5, 0.5, canvas.width - 1.0, canvas.height - 1.0)
    }

    private fun CanvasRenderingContext2D.drawMonster() {
        if (!(Images.monsterReady && Images.monsterEatReady)) return
        val image = if (Game.monsterEat()) Images.monsterEatImage else Images.monsterImage
        val position = Game.monsterPosition()
        drawImage(image, position.first, position.second)
    }

    private fun CanvasRenderingContext2D.drawSki() {
        if (!(Images.skiReady && Images.skiFallReady)) return
        val image = if (Game.skiHit()) Images.skiFallImage else Images.skiImage
        val position = Game.skiPosition()
        drawImage(image, position.first, position.second)
    }

    private fun CanvasRenderingContext2D.drawTrees() {
        if (!Images.treeReady) return
        Game.treePositions().forEach {
            drawImage(Images.treeImage, it.first, it.second)
        }
    }
}


