package mc.fuckoka.economyapi

import mc.fuckoka.economyapi.bukkit.listener.PlayerJoinListener
import mc.fuckoka.economyapi.domain.repository.MoneyTransactionHistoryRepository
import mc.fuckoka.economyapi.domain.repository.WalletRepository
import mc.fuckoka.economyapi.infrastructure.MoneyTransactionHistoryRepositoryImpl
import mc.fuckoka.economyapi.infrastructure.SimpleWalletRepositoryImpl
import org.bukkit.plugin.java.JavaPlugin

class EconomyAPI : JavaPlugin() {
    companion object {
        lateinit var instance: EconomyAPI
            private set
    }

    val walletRepository: WalletRepository = SimpleWalletRepositoryImpl()
    val historyRepository: MoneyTransactionHistoryRepository = MoneyTransactionHistoryRepositoryImpl()

    override fun onEnable() {
        instance = this

        saveDefaultConfig()

        server.pluginManager.registerEvents(PlayerJoinListener(), this)
    }
}
