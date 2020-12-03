plugins {
    kotlin("jvm") version "1.4.20"
}

group = "com.github.tokou"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlinx") }
}

dependencies {
    testImplementation(kotlin("test-junit"))
}

kotlin {
}
