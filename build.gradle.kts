/*
 * This file was generated by the Gradle 'init' task.
 */

val ktorVersion: String by project
val dl4jVersion: String by project
val nd4jVersion: String by project

plugins {
    java
    `maven-publish`
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    kotlin("jvm") version "1.5.31"
}


repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
dependencies {
    implementation(kotlin("stdlib"))
    implementation(group = "com.github.shin285", name = "KOMORAN", version = "3.3.4")
    implementation(group = "org.json", name = "json", version = "20211205")
    implementation(group = "org.nd4j", name = "nd4j-cuda-10.0-platform", version = nd4jVersion)
    implementation(group = "org.deeplearning4j", name = "deeplearning4j-core", version = dl4jVersion)
    implementation(group = "org.deeplearning4j", name = "deeplearning4j-nlp", version = dl4jVersion)
    implementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.6")
    implementation(group = "org.junit.jupiter", name = "junit-jupiter", version = "5.7.0")

}

group = "io.github.ssu-isteam"
version = "1.0-SNAPSHOT"
description = rootProject.name
java.sourceCompatibility = JavaVersion.VERSION_11



nexusPublishing{
    repositories{
        sonatype{
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set("")
            password.set("")
        }
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}




