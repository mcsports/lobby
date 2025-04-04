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
    maven("https://repo.simplecloud.app/snapshots")
    maven("https://buf.build/gen/maven")
}

dependencies {
    compileOnly(libs.kotlin.stdlib)
    testImplementation(libs.kotlin.test)

    implementation("club.mcsports.generated:bindings:1.0-5997ba6") {
        exclude(group = "org.spongepowered")
    }
    implementation("com.noxcrew.interfaces:interfaces:1.3.2")
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
    implementation("org.jooq:jooq:3.20.1")
    implementation("fr.mrmicky:fastboard:2.1.3")
    compileOnly("app.simplecloud.controller:controller-api:0.0.30-dev.e6c9f03")
    compileOnly("app.simplecloud.droplet.player:player-api:0.0.1-dev.d1b6e59")

}

tasks {
    shadowJar {
        mergeServiceFiles()
        exclude("kotlin/**")
        exclude("kotlinx/**")
        archiveFileName = "${project.name}.jar"
    }
    assemble {
        dependsOn(reobfJar)
    }
}