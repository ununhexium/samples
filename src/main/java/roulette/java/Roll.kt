package roulette.java

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Roulette

class Foo(values: Map<String, Any>){
  val a:Int by values
  val b:String by values
}

fun main(args: Array<String>)
{
  SpringApplication.run(Roulette::class.java, *args)
}
