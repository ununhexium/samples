package image

import com.google.common.base.Stopwatch
import org.funktionale.currying.curried
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Polygon
import java.io.File
import java.lang.Math.PI
import java.lang.Math.cos
import java.lang.Math.max
import java.lang.Math.min
import java.lang.Math.sin
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.logging.Logger
import javax.imageio.ImageIO

fun main(args: Array<String>) {
  val log = Logger.getLogger("main")

  val watch = Stopwatch.createStarted()

  log.info("Start +${watch.elapsed()}")
  val input = File("/tmp/screen/in.bmp")

  log.info("Load +${watch.elapsed(MILLISECONDS)}")
  val image = ImageIO.read(input)
  val g = image.graphics as Graphics2D

  val size = 20

  val xs = (0..image.width step size)
  val yStep = (size * cos(PI / 6.0)).toInt()
  val ys = (0..image.height step yStep)

  class P(val x: Int, val y: Int, val c: Int)

  val constrain = { low: Int, high: Int, value: Int ->
    max(low, min(high, value))
  }.curried()

  val constrainX = constrain(0)(image.width - 1)
  val constrainY = constrain(0)(image.height - 1)

  log.info("Extract +${watch.elapsed(MILLISECONDS)}")
  val points =
      ys.mapIndexed { idx, y ->
        val xOffset = if (idx % 2 == 0) size / 2 else 0
        xs.map { x ->
          val a = max(min(image.width, x), 0)
          P(x + xOffset, y, image.getRGB(constrainX(x + xOffset), constrainY(y)))
        }
      }.flatten()
  log.info("Extracted ${points.size} +${watch.elapsed(MILLISECONDS)}")

  log.info("Fill +${watch.elapsed(MILLISECONDS)}")
  points.forEach { p ->
    val hex = Polygon(
        (0..5).map { (size / 2 * cos(it * PI / 3 + PI / 6) / cos(PI/6) + p.x).toInt() }.toIntArray(),
        (0..5).map { (size / 2 * sin(it * PI / 3 + PI / 6) / cos(PI/6) + p.y).toInt() }.toIntArray(),
        6
    )
    g.color = Color(p.c)
    g.fill(hex)
  }

  log.info("Write +${watch.elapsed(MILLISECONDS)}")
  ImageIO.write(image, "BMP", File("/tmp/screen/out.bmp"))

  log.info("End +${watch.elapsed(MILLISECONDS)}")
}

