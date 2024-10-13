plugins {
    id("org.springframework.boot")
    kotlin("plugin.spring")
}

group = "com.crypto.api"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-gateway-mvc:4.0.6")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.1.5")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}


tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}