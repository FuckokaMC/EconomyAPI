import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    kotlin("jvm") version "2.0.21"
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

group = "mc.fuckoka"
version = "1.0-SNAPSHOT"

val mcVersion = "1.21.4"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:${mcVersion}-R0.1-SNAPSHOT")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/fuckokamc/EconomyAPI")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
        publications {
            register<MavenPublication>("gpr") {
                from(components["java"])
            }
        }
    }
}

bukkit {
    name = "EconomyAPI"
    version = getVersion().toString()
    description = "経済プラグイン"
    author = "deceitya"
    main = "mc.fuckoka.economyapi.EconomyAPI"
    apiVersion = mcVersion.substring(0, mcVersion.lastIndexOf("."))
    load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD

    commands {
        register("mymoney") {
            description = "自分の所持金を表示する。"
            permission = "economyapi.commands.mymoney"
            usage = "/mymoney"
        }
        register("paymoney") {
            description = "プレイヤーにお金を支払う。"
            permission = "economyapi.commands.paymoney"
            usage = "/paymoney <プレイヤー名> <金額> [理由]"
        }
        register("showmoney") {
            description = "プレイヤーのお金を表示する。"
            permission = "economyapi.commands.showmoney"
            usage = "/showmoney <プレイヤー名>"
        }
        register("setmoney") {
            description = "プレイヤーの所持金を設定する。"
            permission = "economyapi.commands.setmoney"
            usage = "/setmoney <プレイヤー名> <金額> [理由]"
        }
        register("givemoney") {
            description = "プレイヤーの所持金を増やす。"
            permission = "economyapi.commands.givemoney"
            usage = "/givemoney <プレイヤー名> <金額> [理由]"
        }
        register("takemoney") {
            description = "プレイヤーの所持金を減らす。"
            permission = "economyapi.commands.takemoney"
            usage = "/takemoney <プレイヤー名> <金額> [理由]"
        }
        register("logmoney") {
            description = "所持金のログを表示する。"
            permission = "economyapi.commands.logmoney"
            usage = "/logmoney [ページ]"
        }
    }

    permissions {
        register("economyapi.commands.mymoney") {
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
        register("economyapi.commands.paymoney") {
            default = BukkitPluginDescription.Permission.Default.OP
        }
        register("economyapi.commands.showmoney") {
            default = BukkitPluginDescription.Permission.Default.OP
        }
        register("economyapi.commands.setmoney") {
            default = BukkitPluginDescription.Permission.Default.OP
        }
        register("economyapi.commands.givemoney") {
            default = BukkitPluginDescription.Permission.Default.OP
        }
        register("economyapi.commands.takemoney") {
            default = BukkitPluginDescription.Permission.Default.OP
        }
        register("economyapi.commands.logmoney") {
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
    }
}
