package mc.fuckoka.economyapi.application

import mc.fuckoka.dbconnector.Database
import mc.fuckoka.economyapi.domain.model.Money
import mc.fuckoka.economyapi.domain.model.Reason
import mc.fuckoka.economyapi.domain.repository.MoneyTransactionHistoryRepository
import mc.fuckoka.economyapi.domain.repository.WalletRepository
import java.util.*

class GiveMoneyUseCase(
    private val walletRepository: WalletRepository,
    private val historyRepository: MoneyTransactionHistoryRepository
) {
    /**
     * 所持金を増やす
     *
     * @param player
     * @param amount
     * @param reason
     * @return 増加後の所持金
     */
    fun execute(player: UUID, amount: Int, reason: String? = null): Int? {
        return Database.transaction {
            val wallet = walletRepository.findBy(player) ?: return@transaction null

            val money = Money(amount)
            // お金を受け取れない場合はfalseを返す
            if (!wallet.canCredited(money)) return@transaction null
            val moneyTransaction = wallet.credited(Money(amount), if (reason != null) Reason(reason) else null)

            walletRepository.store(wallet)
            historyRepository.store(moneyTransaction)

            return@transaction wallet.money.value
        }
    }
}
