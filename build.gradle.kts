plugins {
    id("java")
    `maven-publish`
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "com.dinter.config"
version = "1.0-SNAPSHOT"

buildscript {
    extra.apply {
        set("springBootVersion", "3.3.0")
        set("springCloudVersion", "4.1.2")
        set("springKafkaVersion", "3.2.1")
        set("lombokVersion", "1.18.30")
        // Add other versions as needed
    }
}

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

dependencies {
    val lombokVersion = rootProject.extra.get("lombokVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok:$lombokVersion")

    implementation("org.springframework.boot:spring-boot-starter-webflux:${rootProject.extra.get("springBootVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra.get("springBootVersion")}")
    implementation("org.springframework.kafka:spring-kafka:${rootProject.extra.get("springKafkaVersion")}")
    testImplementation("org.springframework.kafka:spring-kafka-test:${rootProject.extra.get("springKafkaVersion")}")
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.72.Final:osx-aarch_64")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}