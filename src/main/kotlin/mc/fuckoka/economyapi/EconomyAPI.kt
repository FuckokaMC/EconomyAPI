package mc.fuckoka.economyapi

import mc.fuckoka.economyapi.config.Database
import org.bukkit.plugin.java.JavaPlugin

class EconomyAPI : JavaPlugin() {
    override fun onDisable() {
        Database.close()
    }
}
