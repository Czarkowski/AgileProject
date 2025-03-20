package pbs.agile.webapi.configurations

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**") // Zezwala na CORS dla wszystkich ścieżek
            .allowedOrigins("http://localhost:5173") // Dopuszczone pochodzenie (frontend)
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Dozwolone metody HTTP
            .allowedHeaders("*") // Dopuszczone nagłówki
            .allowCredentials(true) // Jeśli chcesz używać ciasteczek
    }
}