package mc.fuckoka.economyapi

import mc.fuckoka.economyapi.bukkit.listener.PlayerJoinListener
import mc.fuckoka.economyapi.infrastructure.MoneyTransactionHistoryRepositoryImpl
import mc.fuckoka.economyapi.infrastructure.SimpleWalletRepositoryImpl
import org.bukkit.plugin.java.JavaPlugin

class EconomyAPI : JavaPlugin() {
    companion object {
        lateinit var vault: VaultProvider
            private set
    }

    override fun onEnable() {
        vault = VaultProvider(this, SimpleWalletRepositoryImpl(), MoneyTransactionHistoryRepositoryImpl())
        saveDefaultConfig()

        server.pluginManager.registerEvents(PlayerJoinListener(), this)
    }
}
