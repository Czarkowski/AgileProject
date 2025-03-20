plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25" // Zaktualizowana wersja
    id("org.openapi.generator") version "7.2.0"
}

group = "pbs.agile"
version = "0.0.1-SNAPSHOT"

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "21" // Ustaw JVM 17
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("io.jsonwebtoken:jjwt:0.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(kotlin("stdlib"))
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
//    implementation("org.springdoc:springdoc-openapi-ui:1.8.0")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//openApiGenerate {
//    generatorName.set("typescript-fetch") // Możesz zmienić na np. "typescript-angular" lub "typescript-axios"
//    inputSpec.set("$rootDir/src/main/resources/openapi.yaml") // Ścieżka do pliku OpenAPI
//    outputDir.set("$buildDir/generated") // Katalog, gdzie wygenerowane będą pliki
//    apiPackage.set("com.example.api") // Pakiet API (jeśli generujesz też klienta)
//    modelPackage.set("com.example.model") // Pakiet modeli
//    typeMappings.set(mapOf("LocalDateTime" to "string")) // Opcjonalne mapowania typów
//}
//
//tasks.named("compileKotlin") {
//    dependsOn("openApiGenerate")
//}