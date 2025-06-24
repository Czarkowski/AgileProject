package pbs.agile.webapi.configurations

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.security.SecurityRequirement
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(info = io.swagger.v3.oas.annotations.info.Info(title = "Api V1", version = "1"))
class SwaggerConfig {

    @Bean
    fun publicApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("public")
            .pathsToMatch("/api/**", "/auth/**")  // Tylko ścieżki, które chcesz uwzględnić w tym grupie
            .build()
    }

    @Bean
    fun apiDocs(): OpenAPI {
        return OpenAPI()
            .components(
                Components()
                    .addSecuritySchemes("bearerAuth",
                        SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
                    .addSecuritySchemes("basicAuth",
                        SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")
                    )
            )
            .addSecurityItem(
                SecurityRequirement().addList("bearerAuth")
            )
            .addSecurityItem(
                SecurityRequirement().addList("basicAuth")
            )
    }
}