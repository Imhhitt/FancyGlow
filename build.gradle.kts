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

    maven("https://jitpack.io/")
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
    compileOnly(libs.com.github.neznamy.tab.api)
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
version = "2.9.4"

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
        apiVersion = "1.19"
        authors = listOf("hhitt")
        contributors = listOf("Sliide_")
        load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
        defaultPermission = BukkitPluginDescription.Permission.Default.FALSE
        softDepend = listOf(
            "TAB",
            "PlaceholderAPI",
        )

        permissions {
            // Admin permission
            register("fancyglow.admin") {
                default = BukkitPluginDescription.Permission.Default.OP
                description = "Gives permission to every FancyGlow command and feature."
                children = listOf(
                    "fancyglow.rainbow", "fancyglow.flashing", "fancyglow.all_colors", "fancyglow.command.gui",
                    "fancyglow.command.color", "fancyglow.command.reload", "fancyglow.command.disable",
                    "fancyglow.command.disable.everyone", "fancyglow.command.disable.others"
                )
            }
            // Commands related permissions
            register("fancyglow.command.gui") {
                default = BukkitPluginDescription.Permission.Default.FALSE
                description = "Gives access to color selector inventory."
            }
            register("fancyglow.command.disable") {
                default = BukkitPluginDescription.Permission.Default.TRUE
                description = "Gives access to disable player own color."
            }
            register("fancyglow.command.color") {
                default = BukkitPluginDescription.Permission.Default.TRUE
                description = "Gives access to select player own glow."
            }
            register("fancyglow.command.disable.others") {
                default = BukkitPluginDescription.Permission.Default.OP
                description = "Gives access to disable other players glow."
            }
            register("fancyglow.command.disable.everyone") {
                default = BukkitPluginDescription.Permission.Default.OP
                description = "Gives access to disable the whole server glow."
            }
            register("fancyglow.command.reload") {
                default = BukkitPluginDescription.Permission.Default.OP
                description = "Gives access to reload plugin config."
            }
            // Color related permissions
            register("fancyglow.all_colors") {
                default = BukkitPluginDescription.Permission.Default.FALSE
                description = "Gives permission to every color."
                children = listOf(
                    "fancyglow.rainbow", "fancyglow.flashing",
                    "fancyglow.black", "fancyglow.white",
                    "fancyglow.red", "fancyglow.dark_red",
                    "fancyglow.gray", "fancyglow.dark_gray",
                    "fancyglow.blue", "fancyglow.dark_blue",
                    "fancyglow.aqua", "fancyglow.dark_aqua",
                    "fancyglow.green", "fancyglow.dark_green",
                    "fancyglow.yellow", "fancyglow.gold",
                    "fancyglow.light_purple", "fancyglow.dark_purple"
                )
            }
            register("fancyglow.rainbow") {
                default = BukkitPluginDescription.Permission.Default.FALSE
                description = "Gives access to rainbow mode."
            }
            register("fancyglow.flashing") {
                default = BukkitPluginDescription.Permission.Default.FALSE
                description = "Gives access to flashing mode."
            }
            // Color permissions
            register("fancyglow.red")
            register("fancyglow.gold")
            register("fancyglow.gray")
            register("fancyglow.blue")
            register("fancyglow.aqua")
            register("fancyglow.black")
            register("fancyglow.white")
            register("fancyglow.green")
            register("fancyglow.yellow")
            register("fancyglow.dark_red")
            register("fancyglow.dark_blue")
            register("fancyglow.dark_gray")
            register("fancyglow.dark_aqua")
            register("fancyglow.dark_green")
            register("fancyglow.dark_purple")
            register("fancyglow.light_purple")
        }
    }
}