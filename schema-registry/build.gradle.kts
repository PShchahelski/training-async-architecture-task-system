plugins {
    kotlin("jvm") version "1.8.22"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.8.0"
}

group = "com.training.scheme.registry"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.apache.avro:avro:1.11.1")
}