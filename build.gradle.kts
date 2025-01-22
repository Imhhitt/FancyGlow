import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import revxrsal.zapper.gradle.zapper

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("io.github.revxrsal.zapper") version "1.0.3"
}

repositories {
    mavenLocal()
    mavenCentral()

    maven("https://libraries.minecraft.net/")
    maven("https://oss.sonatype.org/content/groups/public")
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi")
}

dependencies {
    compileOnly(libs.com.mojang.authlib)
    compileOnly(libs.me.clip.placeholderapi)
    compileOnly(libs.org.spigotmc.spigot.api)
    compileOnly(libs.com.github.retrooper.packetevents.spigot)

    zap(libs.net.kyori.adventure.api)
    zap(libs.org.bstats.bstats.bukkit)
    zap(libs.dev.dejvokep.boosted.yaml)
    zap(libs.io.github.revxrsal.lamp.common)
    zap(libs.io.github.revxrsal.lamp.bukkit)
    zap(libs.net.kyori.adventure.platform.bukkit)
    zap(libs.net.kyori.adventure.text.minimessage)
}

group = "hhitt.fancyglow"
description = "FancyGlow"
version = "2.7.3-BETA"

zapper {
    libsFolder = "libraries"
    relocationPrefix = "${group}.deps"

    repositories { includeProjectRepositories() }

    relocate("net.kyori", "kyori")
    relocate("org.bstats", "bstats")
    relocate("revxrsal.commands", "lamp")
    relocate("dev.dejvokep.boostedyaml", "boostedyaml")
}

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
                    "fancyglow.rainbow", "fancyglow.flashing", "fancyglow.all_colors", "fancyglow.command.gui",
                    "fancyglow.command.color", "fancyglow.command.reload", "fancyglow.command.disable",
                    "fancyglow.command.disable.everyone", "fancyglow.command.disable.others"
                )
            }
        }
    }
}