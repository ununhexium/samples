package roulette.java.shell

import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import java.util.*


@ShellComponent("Roulette")
class RouletteShell
{
  companion object
  {
    val log: Logger by lazy {
      LoggerFactory.getLogger(RouletteShell::class.java)
    }
  }

  val raw by lazy {
    val classLoadersList = LinkedList<ClassLoader>()
    classLoadersList.add(ClasspathHelper.contextClassLoader())
    classLoadersList.add(ClasspathHelper.staticClassLoader())

    val reflections = Reflections(
        ConfigurationBuilder()
            .setScanners(
                SubTypesScanner(false /* don't exclude Object.class */), ResourcesScanner()
            )
            .setUrls(
                ClasspathHelper.forClassLoader(*classLoadersList.toTypedArray())
            )
            .filterInputsBy(
                FilterBuilder().include(".*")
            )
    )

    reflections.getSubTypesOf(Any::class.java) as Set<Class<*>>
  }

  val random by lazy { Random() }

  @ShellMethod("Lists all the playable elements")
  fun list(): String
  {
    return raw.joinToString("\n") { it.toString() } + "\n" + raw.size + " entries"
  }

  @ShellMethod("Play java roulette")
  fun play(
      @ShellOption contains: String,
      @ShellOption includeInner: Boolean = false,
      @ShellOption includeAnonymous: Boolean = false
  ): String
  {
    val gameSet = raw.filter {
      if (includeInner) true
      else !it.toString().contains("$")
    }.filter {
      if (includeAnonymous) true
      else !it.toString().contains(Regex("\\\$[0-9]+"))
    }.filter {
      it.toString().substringAfter(" ").contains(contains)
    }

    return if (gameSet.isEmpty()) "No match for $contains"
    else gameSet[random.nextInt(gameSet.size)].toString()
  }
}