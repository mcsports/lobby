import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.shadow)
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.kotlin.test)
}

tasks {
    shadowJar {
        mergeServiceFiles()

        archiveFileName = "${project.name}.jar"
    }
}