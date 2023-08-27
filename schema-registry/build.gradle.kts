plugins {
	kotlin("jvm") version "1.8.22"
	id("com.github.davidmc24.gradle.plugin.avro") version "1.8.0"
	id("com.github.imflog.kafka-schema-registry-gradle-plugin") version "1.11.1"
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

schemaRegistry {
	url = "http://localhost:8081/"
	outputDirectory = "/Users/pshchahelski/Workspace/Experiments/"
	compatibility {
		subject("test1-task-lifecycle", "src/main/avro/streaming/taskStreamingEvent_v2.avsc", "AVRO")
			.addLocalReference("eventMeta", "src/main/avro/meta/eventMeta_v1.avsc")
	}

	register {
		subject("test1-task-lifecycle", "src/main/avro/streaming/taskStreamingEvent_v1.avsc", "AVRO")
			.addLocalReference("eventMeta", "src/main/avro/meta/eventMeta_v1.avsc")
	}

	download {
		subject("test1-task-lifecycle", "/Users/pshchahelski/Workspace/Experiments/")
	}
}