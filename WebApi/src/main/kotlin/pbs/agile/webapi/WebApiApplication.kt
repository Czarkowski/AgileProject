package pbs.agile.webapi

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
//import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
//@OpenAPIDefinition
class WebApiApplication

fun main(args: Array<String>) {
    runApplication<WebApiApplication>(*args)
}
