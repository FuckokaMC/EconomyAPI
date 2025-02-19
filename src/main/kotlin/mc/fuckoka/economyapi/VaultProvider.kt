package mc.fuckoka.economyapi

import mc.fuckoka.economyapi.domain.repository.MoneyTransactionHistoryRepository
import mc.fuckoka.economyapi.domain.repository.WalletRepository
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

class VaultProvider(
    private val plugin: EconomyAPI,
    private val walletRepository: WalletRepository,
    private val historyRepository: MoneyTransactionHistoryRepository
) : Economy {
    override fun isEnabled(): Boolean {
        return plugin.isEnabled
    }

    override fun getName(): String {
        return plugin.name
    }

    override fun hasBankSupport(): Boolean {
        return false
    }

    override fun fractionalDigits(): Int {
        return 0
    }

    override fun format(amount: Double): String {
        return "%,d".format(amount)
    }

    override fun currencyNamePlural(): String {
        return "円"
    }

    override fun currencyNameSingular(): String {
        return "円"
    }

    override fun hasAccount(player: OfflinePlayer): Boolean {
        TODO("Not yet implemented")
    }

    override fun getBalance(player: OfflinePlayer): Double {
        TODO("Not yet implemented")
    }

    override fun has(player: OfflinePlayer, amount: Double): Boolean {
        TODO("Not yet implemented")
    }

    override fun withdrawPlayer(player: OfflinePlayer, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun depositPlayer(player: OfflinePlayer, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun createPlayerAccount(player: OfflinePlayer): Boolean {
        TODO("Not yet implemented")
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("hasAccount(Bukkit.getOfflinePlayer(playerName))", "org.bukkit.Bukkit")
    )
    override fun hasAccount(playerName: String): Boolean {
        return hasAccount(Bukkit.getOfflinePlayer(playerName))
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("hasAccount(Bukkit.getOfflinePlayer(playerName))", "org.bukkit.Bukkit")
    )
    override fun hasAccount(playerName: String, worldName: String): Boolean {
        return hasAccount(Bukkit.getOfflinePlayer(playerName))
    }

    override fun hasAccount(player: OfflinePlayer, worldName: String): Boolean {
        return hasAccount(player)
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("getBalance(Bukkit.getOfflinePlayer(playerName))", "org.bukkit.Bukkit")
    )
    override fun getBalance(playerName: String): Double {
        return getBalance(Bukkit.getOfflinePlayer(playerName))
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("getBalance(Bukkit.getOfflinePlayer(playerName))", "org.bukkit.Bukkit")
    )
    override fun getBalance(playerName: String, world: String): Double {
        return getBalance(Bukkit.getOfflinePlayer(playerName))
    }

    override fun getBalance(player: OfflinePlayer, world: String): Double {
        return getBalance(player)
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("has(Bukkit.getOfflinePlayer(playerName), amount)", "org.bukkit.Bukkit")
    )
    override fun has(playerName: String, amount: Double): Boolean {
        return has(Bukkit.getOfflinePlayer(playerName), amount)
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("has(Bukkit.getOfflinePlayer(playerName), amount)", "org.bukkit.Bukkit")
    )
    override fun has(playerName: String, worldName: String, amount: Double): Boolean {
        return has(Bukkit.getOfflinePlayer(playerName), amount)
    }

    override fun has(player: OfflinePlayer, worldName: String, amount: Double): Boolean {
        return has(player, amount)
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("withdrawPlayer(Bukkit.getOfflinePlayer(playerName), amount)", "org.bukkit.Bukkit")
    )
    override fun withdrawPlayer(playerName: String, amount: Double): EconomyResponse {
        return withdrawPlayer(Bukkit.getOfflinePlayer(playerName), amount)
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("withdrawPlayer(Bukkit.getOfflinePlayer(playerName), amount)", "org.bukkit.Bukkit")
    )
    override fun withdrawPlayer(playerName: String, worldName: String, amount: Double): EconomyResponse {
        return withdrawPlayer(Bukkit.getOfflinePlayer(playerName), amount)
    }

    override fun withdrawPlayer(player: OfflinePlayer, worldName: String, amount: Double): EconomyResponse {
        return withdrawPlayer(player, amount)
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("depositPlayer(Bukkit.getOfflinePlayer(playerName), amount)", "org.bukkit.Bukkit")
    )
    override fun depositPlayer(playerName: String, amount: Double): EconomyResponse {
        return depositPlayer(Bukkit.getOfflinePlayer(playerName), amount)
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("depositPlayer(Bukkit.getOfflinePlayer(playerName), amount)", "org.bukkit.Bukkit")
    )
    override fun depositPlayer(playerName: String, worldName: String, amount: Double): EconomyResponse {
        return depositPlayer(Bukkit.getOfflinePlayer(playerName), amount)
    }

    override fun depositPlayer(player: OfflinePlayer, worldName: String, amount: Double): EconomyResponse {
        return depositPlayer(player, amount)
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("createPlayerAccount(Bukkit.getOfflinePlayer(playerName))", "org.bukkit.Bukkit")
    )
    override fun createPlayerAccount(playerName: String): Boolean {
        return createPlayerAccount(Bukkit.getOfflinePlayer(playerName))
    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("createPlayerAccount(Bukkit.getOfflinePlayer(playerName))", "org.bukkit.Bukkit")
    )
    override fun createPlayerAccount(playerName: String, worldName: String): Boolean {
        return createPlayerAccount(Bukkit.getOfflinePlayer(playerName))
    }

    override fun createPlayerAccount(player: OfflinePlayer, worldName: String): Boolean {
        return createPlayerAccount(player)
    }

    @Deprecated("Deprecated in Java")
    override fun createBank(name: String, player: String): EconomyResponse {
        throw NotImplementedError()
    }

    override fun createBank(name: String, player: OfflinePlayer): EconomyResponse {
        throw NotImplementedError()
    }

    override fun deleteBank(name: String): EconomyResponse {
        throw NotImplementedError()
    }

    override fun bankBalance(name: String): EconomyResponse {
        throw NotImplementedError()
    }

    override fun bankHas(name: String, amount: Double): EconomyResponse {
        throw NotImplementedError()
    }

    override fun bankWithdraw(name: String, amount: Double): EconomyResponse {
        throw NotImplementedError()
    }

    override fun bankDeposit(name: String, amount: Double): EconomyResponse {
        throw NotImplementedError()
    }

    @Deprecated("Deprecated in Java")
    override fun isBankOwner(name: String, playerName: String): EconomyResponse {
        throw NotImplementedError()
    }

    override fun isBankOwner(name: String, player: OfflinePlayer): EconomyResponse {
        throw NotImplementedError()
    }

    @Deprecated("Deprecated in Java")
    override fun isBankMember(name: String, playerName: String): EconomyResponse {
        throw NotImplementedError()
    }

    override fun isBankMember(name: String, player: OfflinePlayer): EconomyResponse {
        throw NotImplementedError()
    }

    override fun getBanks(): MutableList<String> {
        throw NotImplementedError()
    }
}
