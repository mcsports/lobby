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
    maven("https://repo.mcsports.club/snapshots")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.noxcrew.com/public")
    maven("https://repo.simplecloud.app/snapshots")
    maven("https://buf.build/gen/maven")
}

dependencies {
    compileOnly(libs.kotlin.stdlib)
    testImplementation(libs.kotlin.test)

    implementation("club.mcsports.generated:bindings:1.0-9c172dd") {
        exclude(group = "org.spongepowered")
    }

    paperweight.paperDevBundle(libs.versions.paper.api.get())

    implementation(libs.interfaces)
    implementation(libs.jooq)
    implementation(libs.fastboard)
    implementation(libs.mcsports.queue)

    compileOnly(libs.simplecloud.controller)
    compileOnly(libs.simplecloud.player)
    compileOnly(libs.luckperms)
}

tasks {
    shadowJar {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        mergeServiceFiles()
        exclude("kotlin/**")
        exclude("kotlinx/**")
        relocate("io.grpc", "club.mcsports.lobby.relocate.io.grpc")
        relocate("com.google.protobuf", "club.mcsports.lobby.relocate.google.protobuf")
        relocate("com.google.common", "club.mcsports.lobby.relocate.google.common")
        archiveFileName = "${project.name}.jar"
    }
    assemble {
        dependsOn(reobfJar)
    }
}