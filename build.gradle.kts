import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.shadow)
    alias(libs.plugins.paperweight.userdev)
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
    maven("https://repo.mcsports.club/releases")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.noxcrew.com/public")
}

dependencies {
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.kotlin.test)

    implementation("club.mcsports.generated:bindings:1.0-36f6981")
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    implementation("com.noxcrew.interfaces:interfaces:1.3.2")
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
    implementation("org.jooq:jooq:3.20.1")
}

tasks {
    shadowJar {
        mergeServiceFiles()

        archiveFileName = "${project.name}.jar"
    }
    assemble {
        dependsOn(reobfJar)
    }
}