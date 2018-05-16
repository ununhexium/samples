package roulette.java

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Roulette

fun main(args: Array<String>)
{
  SpringApplication.run(Roulette::class.java, *args)
}