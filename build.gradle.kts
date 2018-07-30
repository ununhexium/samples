import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val assertJVersion = "3.9.1"
val jmhVersion = "1.21"
val junitPlatformVersion = "1.0.1"
val junitJupiterVersion = "5.1.0"
val reflectionsVersion = "0.9.11"

buildscript {
  repositories {
    mavenCentral()
    jcenter()
  }
  dependencies {
    val kotlinVersion = "1.2.51"
    classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.+")
  }
}

plugins {
  val kotlinVersion = "1.2.51"
  idea
  java
  id("org.jetbrains.kotlin.jvm") version kotlinVersion
  id("org.springframework.boot") version "2.0.2.RELEASE"
  id("io.spring.dependency-management") version "1.0.5.RELEASE"
  id ("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
}

apply {
  plugin("kotlin-spring")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

idea {
  module {
    isDownloadSources = true
  }
}

repositories {
  mavenLocal()
  mavenCentral()
  jcenter()
}

version = "0.0.1-SNAPSHOT"

dependencies {
  compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  compile("org.jetbrains.kotlin:kotlin-reflect")

  compile("org.funktionale:funktionale-currying:1.2")

  compile("net.lab0.kotlin.more:morekotlin:0.1.4")

  compile("org.openjdk.jmh:jmh-core:$jmhVersion")
  compile("org.openjdk.jmh:jmh-generator-annprocess:$jmhVersion")

  compile("org.reflections:reflections:$reflectionsVersion")

  compile("org.springframework:spring-core")
  compile("org.springframework.shell:spring-shell-starter:2.0.1.RELEASE")

  testImplementation("org.assertj:assertj-core:$assertJVersion")
  testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")

  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.withType<Jar> {
  configurations["compileClasspath"].forEach { file: File ->
    from(zipTree(file.absoluteFile))
  }
  manifest {
    attributes(
        mapOf(
            "Main-Class" to "net.lab0.shell.Application"
        )
    )
  }
}

tasks {
  task<Exec>("htmlDeps") {
    dependsOn("htmlDependencyReport")
    val browser = "/usr/bin/sensible-browser"
    commandLine(browser, "build/reports/project/dependencies/index.html")
  }
}