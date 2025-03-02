package pbs.agile.webapi.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping


@RestController
@RequestMapping("/api")
@Tag(name = "Example API")
class ExampleController {

    @GetMapping("/hello")
    @Operation(summary = "Get example")
    fun hello(): String {
        return "Hello, World!"
    }
}
