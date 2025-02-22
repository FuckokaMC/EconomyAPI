package mc.fuckoka.economyapi

import mc.fuckoka.economyapi.application.*
import mc.fuckoka.economyapi.domain.model.MoneyTransaction
import mc.fuckoka.economyapi.domain.repository.MoneyTransactionHistoryRepository
import mc.fuckoka.economyapi.domain.repository.WalletRepository
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import kotlin.math.floor

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
        try {
            return FindMoneyUseCase(walletRepository).execute(player.uniqueId) != null
        } catch (e: Exception) {
            plugin.logger.warning(e.stackTraceToString())
            return false
        }
    }

    override fun getBalance(player: OfflinePlayer): Double {
        try {
            return (FindMoneyUseCase(walletRepository).execute(player.uniqueId) ?: -1).toDouble()
        } catch (e: Exception) {
            plugin.logger.warning(e.stackTraceToString())
        }
        return -1.0
    }

    override fun has(player: OfflinePlayer, amount: Double): Boolean {
        try {
            val balance = FindMoneyUseCase(walletRepository).execute(player.uniqueId)
            return if (balance == null) false else floor(amount) <= balance
        } catch (e: Exception) {
            plugin.logger.warning(e.stackTraceToString())
            return false
        }
    }

    override fun withdrawPlayer(player: OfflinePlayer, amount: Double): EconomyResponse {
        try {
            val fAmount = floor(amount)
            val balance =
                TakeMoneyUseCase(walletRepository, historyRepository).execute(player.uniqueId, fAmount.toInt())
            if (balance != null) {
                return EconomyResponse(fAmount, balance.toDouble(), EconomyResponse.ResponseType.SUCCESS, "")
            }
        } catch (e: Exception) {
            plugin.logger.warning(e.stackTraceToString())
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "")
    }

    override fun depositPlayer(player: OfflinePlayer, amount: Double): EconomyResponse {
        try {
            val fAmount = floor(amount)
            val balance =
                GiveMoneyUseCase(walletRepository, historyRepository).execute(player.uniqueId, fAmount.toInt())
            if (balance != null) {
                return EconomyResponse(fAmount, balance.toDouble(), EconomyResponse.ResponseType.SUCCESS, "")
            }
        } catch (e: Exception) {
            plugin.logger.warning(e.stackTraceToString())
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "")
    }

    fun pay(from: OfflinePlayer, to: OfflinePlayer, amount: Double): Pair<EconomyResponse, EconomyResponse> {
        try {
            val fAmount = floor(amount)
            val balance = PayMoneyUseCase(walletRepository, historyRepository).execute(
                from.uniqueId,
                to.uniqueId,
                fAmount.toInt()
            )
            if (balance != null) {
                return Pair(
                    EconomyResponse(fAmount, balance.first.toDouble(), EconomyResponse.ResponseType.SUCCESS, ""),
                    EconomyResponse(fAmount, balance.second.toDouble(), EconomyResponse.ResponseType.SUCCESS, "")
                )
            }
        } catch (e: Exception) {
            plugin.logger.warning(e.stackTraceToString())
        }

        return Pair(
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, ""),
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "")
        )
    }

    override fun createPlayerAccount(player: OfflinePlayer): Boolean {
        try {
            return CreateWalletUseCase(walletRepository, historyRepository).execute(
                player.uniqueId,
                plugin.config.getInt("default-money")
            )
        } catch (e: Exception) {
            plugin.logger.warning(e.stackTraceToString())
            return false
        }
    }

    fun getHistories(player: OfflinePlayer, page: Int): Pair<List<MoneyTransaction>, Int>? {
        try {
            val result = FindHistoriesUseCase(walletRepository, historyRepository).execute(
                player.uniqueId,
                plugin.config.getInt("log-count") * (page - 1),
                plugin.config.getInt("log-count")
            )
            return Pair(result.first, (result.second / plugin.config.getLong("log-count")).toInt())
        } catch (e: Exception) {
            plugin.logger.warning(e.stackTraceToString())
        }
        return null
    }


    // 以下、あまり使わないメソッド
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Deprecated(
        "Deprecated in Java", ReplaceWith("hasAccount(Bukkit.getOfflinePlayer(playerName))", "org.bukkit.Bukkit")
    )
    override fun hasAccount(playerName: String): Boolean {
        return hasAccount(Bukkit.getOfflinePlayer(playerName))
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith("hasAccount(Bukkit.getOfflinePlayer(playerName))", "org.bukkit.Bukkit")
    )
    override fun hasAccount(playerName: String, worldName: String): Boolean {
        return hasAccount(Bukkit.getOfflinePlayer(playerName))
    }

    override fun hasAccount(player: OfflinePlayer, worldName: String): Boolean {
        return hasAccount(player)
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith("getBalance(Bukkit.getOfflinePlayer(playerName))", "org.bukkit.Bukkit")
    )
    override fun getBalance(playerName: String): Double {
        return getBalance(Bukkit.getOfflinePlayer(playerName))
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith("getBalance(Bukkit.getOfflinePlayer(playerName))", "org.bukkit.Bukkit")
    )
    override fun getBalance(playerName: String, world: String): Double {
        return getBalance(Bukkit.getOfflinePlayer(playerName))
    }

    override fun getBalance(player: OfflinePlayer, world: String): Double {
        return getBalance(player)
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith("has(Bukkit.getOfflinePlayer(playerName), amount)", "org.bukkit.Bukkit")
    )
    override fun has(playerName: String, amount: Double): Boolean {
        return has(Bukkit.getOfflinePlayer(playerName), amount)
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith("has(Bukkit.getOfflinePlayer(playerName), amount)", "org.bukkit.Bukkit")
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

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, \"\")",
            "net.milkbowl.vault.economy.EconomyResponse",
            "net.milkbowl.vault.economy.EconomyResponse"
        )
    )
    override fun createBank(name: String, player: String): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "")
    }

    override fun createBank(name: String, player: OfflinePlayer): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "")
    }

    override fun deleteBank(name: String): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "")
    }

    override fun bankBalance(name: String): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "")
    }

    override fun bankHas(name: String, amount: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "")
    }

    override fun bankWithdraw(name: String, amount: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "")
    }

    override fun bankDeposit(name: String, amount: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "")
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, \"\")",
            "net.milkbowl.vault.economy.EconomyResponse",
            "net.milkbowl.vault.economy.EconomyResponse"
        )
    )
    override fun isBankOwner(name: String, playerName: String): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "")
    }

    override fun isBankOwner(name: String, player: OfflinePlayer): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "")
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
