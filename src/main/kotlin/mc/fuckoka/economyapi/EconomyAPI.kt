package mc.fuckoka.economyapi

import mc.fuckoka.economyapi.bukkit.command.MoneyCommand
import mc.fuckoka.economyapi.bukkit.listener.PlayerJoinListener
import mc.fuckoka.economyapi.infrastructure.ConsistentWalletRepositoryImpl
import mc.fuckoka.economyapi.infrastructure.MoneyTransactionHistoryRepositoryImpl
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin

class EconomyAPI : JavaPlugin() {
    lateinit var vault: VaultProvider
        private set
    lateinit var messages: FileConfiguration
        private set

    override fun onEnable() {
        saveDefaultConfig()
        vault = VaultProvider(this, ConsistentWalletRepositoryImpl(), MoneyTransactionHistoryRepositoryImpl())
        messages = YamlConfiguration()
        messages.setDefaults(YamlConfiguration.loadConfiguration(getResource("messages.yml")!!.reader()))

        getCommand("money")?.setExecutor(MoneyCommand(this))
        server.pluginManager.registerEvents(PlayerJoinListener(vault), this)
    }
}
