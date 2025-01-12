import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

repositories {
    mavenLocal()
    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi")
    maven("https://libraries.minecraft.net/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.codemc.io/repository/maven-releases/")
}

dependencies {
    compileOnly(libs.org.spigotmc.spigot.api)
    compileOnly(libs.com.mojang.authlib)
    compileOnly(libs.com.github.retrooper.packetevents.spigot)
    compileOnly(libs.me.clip.placeholderapi)

    implementation(libs.net.kyori.adventure.platform.bukkit)
    implementation(libs.org.bstats.bstats.bukkit)
    implementation(libs.net.kyori.adventure.api)
    implementation(libs.io.github.revxrsal.lamp.common)
    implementation(libs.io.github.revxrsal.lamp.bukkit)
    implementation(libs.net.kyori.adventure.text.minimessage)
}

group = "hhitt.fancyglow"
description = "FancyGlow"
version = "2.4.0"

tasks {

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    compileJava {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }

    shadowJar {
        archiveFileName.set("FancyGlow-${project.version}.jar")

        destinationDirectory.set(file("$rootDir/jars/"))

        //TODO Needs dependencies relocation.
        //relocate("com.alessiodp.libby", "${project.group}.deps.libby")
    }

    bukkit {
        name = "FancyGlow"
        description = "Plugin that allow the player to change its glowing color."
        prefix = name
        version = project.version.toString()
        main = project.group.toString() + ".FancyGlow"
        apiVersion = "1.20"
        authors = listOf("hhitt")
        contributors = listOf("Sliide_")
        softDepend = listOf(
            "TAB",
            "PlaceholderAPI",
        )

        permissions {
            register("fancyglow.admin") {
                default = BukkitPluginDescription.Permission.Default.OP
                description = "Gives permission to every FancyGlow command and feature."
                children = listOf(
                    "fancyglow.rainbow", "fancyglow.all_colors", "fancyglow.command.gui",
                    "fancyglow.command.color", "fancyglow.command.reload", "fancyglow.command.disable",
                    "fancyglow.command.disable.everyone", "fancyglow.command.disable.others"
                )
            }
        }
    }
}