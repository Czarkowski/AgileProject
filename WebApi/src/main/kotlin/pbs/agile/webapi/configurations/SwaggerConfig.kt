package pbs.agile.webapi.configurations//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import springfox.documentation.builders.PathSelectors
//import springfox.documentation.builders.RequestHandlerSelectors
//import springfox.documentation.spi.DocumentationType
//import springfox.documentation.spring.web.plugins.Docket
//import springfox.documentation.swagger2.annotations.EnableSwagger2
//
//@Configuration
//@EnableSwagger2
//class pbs.agile.webapi.configurations.SwaggerConfig {
//    @Bean
//    fun api(): Docket {
//        return Docket(DocumentationType.SWAGGER_2)
//            .select()
//            .apis(RequestHandlerSelectors.any())
//            .paths(PathSelectors.any())
//            .build()
//    }
//}

//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springdoc.core.GroupedOpenApi
//
//@Configuration
//class pbs.agile.webapi.configurations.SwaggerConfig {
//    @Bean
//    fun customApi(): GroupedOpenApi {
//        return GroupedOpenApi.builder()
//            .group("custom-api")
//            .pathsToMatch("/api/**") // Zmień na swoje ścieżki
//            .build()
//    }
//}
//

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.models.GroupedOpenApi

@Configuration
@OpenAPIDefinition(info = io.swagger.v3.oas.annotations.info.Info(title = "Api V1", version = "1"))
class SwaggerConfig : WebMvcConfigurer {

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
            )
            .addSecurityItem(
                SecurityRequirement().addList("bearerAuth")
            )
    }
}