package mc.fuckoka.economyapi

import mc.fuckoka.economyapi.bukkit.command.MoneyCommand
import mc.fuckoka.economyapi.bukkit.listener.PlayerJoinListener
import mc.fuckoka.economyapi.infrastructure.ConsistentWalletRepositoryImpl
import mc.fuckoka.economyapi.infrastructure.MoneyTransactionHistoryRepositoryImpl
import org.bukkit.plugin.java.JavaPlugin

class EconomyAPI : JavaPlugin() {
    lateinit var vault: VaultProvider
        private set

    override fun onEnable() {
        saveDefaultConfig()
        vault = VaultProvider(this, ConsistentWalletRepositoryImpl(), MoneyTransactionHistoryRepositoryImpl())

        getCommand("money")?.setExecutor(MoneyCommand())
        server.pluginManager.registerEvents(PlayerJoinListener(vault), this)
    }
}
