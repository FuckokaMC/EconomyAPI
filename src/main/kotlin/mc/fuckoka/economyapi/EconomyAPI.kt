package mc.fuckoka.economyapi

import mc.fuckoka.economyapi.bukkit.command.*
import mc.fuckoka.economyapi.bukkit.listener.PlayerJoinListener
import mc.fuckoka.economyapi.infrastructure.MoneyTransactionHistoryRepositoryImpl
import mc.fuckoka.economyapi.infrastructure.WalletRepositoryImpl
import net.milkbowl.vault.economy.Economy
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin

class EconomyAPI : JavaPlugin() {
    lateinit var vault: VaultProvider
        private set
    lateinit var messages: FileConfiguration
        private set

    override fun onEnable() {
        saveDefaultConfig()
        vault = VaultProvider(this, WalletRepositoryImpl(), MoneyTransactionHistoryRepositoryImpl())
        messages = YamlConfiguration()
        messages.setDefaults(YamlConfiguration.loadConfiguration(getResource("messages.yml")!!.reader()))

        server.servicesManager.register(Economy::class.java, vault, this, ServicePriority.Normal)

        val moneyCommand = MoneyCommand(this)
        moneyCommand.registerSubCommands(
            GiveCommand(this),
            HelpCommand(this),
            LogCommand(this),
            PayCommand(this),
            TakeCommand(this),
            ValidateCommand(this)
        )
        getCommand("money")?.setExecutor(moneyCommand)
        server.pluginManager.registerEvents(PlayerJoinListener(vault), this)
    }
}
