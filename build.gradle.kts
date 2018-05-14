import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val assertJVersion = "3.9.1"
val junitPlatformVersion = "1.0.1"
val junitJupiterVersion = "5.1.0"

plugins {
  val kotlinVersion = "1.2.41"
  id("org.jetbrains.kotlin.jvm") version kotlinVersion
  java
  idea
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile>{
  kotlinOptions{
    jvmTarget = "1.8"
  }
}

idea {
  module {
    isDownloadSources = true
  }
}

repositories {
  mavenCentral()
  jcenter()
}

version = "0.0.1-SNAPSHOT"

dependencies {
  compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  compile("org.jetbrains.kotlin:kotlin-reflect")
  compile("org.funktionale:funktionale-currying:1.2")

  testImplementation("org.assertj:assertj-core:$assertJVersion")
  testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")

  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
}

buildscript {
  repositories {
    mavenCentral()
    jcenter()
  }
  dependencies {
    classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.+")
  }
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

