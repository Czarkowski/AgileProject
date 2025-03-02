package pbs.agile.webapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
//@OpenAPIDefinition
class WebApiApplication

fun main(args: Array<String>) {
    runApplication<WebApiApplication>(*args)
}
