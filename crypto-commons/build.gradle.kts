plugins {
    id("org.springframework.boot")
    kotlin("plugin.spring")
}
group = "com.crypto.commons"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}