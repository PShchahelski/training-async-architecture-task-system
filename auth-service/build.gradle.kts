import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.2"
	id("io.spring.dependency-management") version "1.1.2"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.0"
}

group = "com.training"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
	maven(url = "https://packages.confluent.io/maven/")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.jsonwebtoken:jjwt:0.9.1")

	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.apache.avro:avro:1.11.1")
	implementation("io.confluent:kafka-avro-serializer:5.3.0")
//	implementation("io.confluent:kafka-streams-avro-serde:5.3.0")
//	implementation("io.confluent:kafka-schema-registry-client:5.3.0")

	implementation(files("../schema-registry/build/libs/schema-registry-1.0-SNAPSHOT.jar"))

	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("javax.xml.bind:jaxb-api:2.3.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
