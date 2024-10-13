plugins {
    kotlin("jvm")
}

group = "com.crypto.commons"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // No dependencies required as it contains only DTOs
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}