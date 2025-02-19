package mc.fuckoka.economyapi.application

import mc.fuckoka.dbconnector.Database
import mc.fuckoka.economyapi.domain.model.Money
import mc.fuckoka.economyapi.domain.model.Reason
import mc.fuckoka.economyapi.domain.repository.MoneyTransactionHistoryRepository
import mc.fuckoka.economyapi.domain.repository.WalletRepository
import java.util.*

class TakeMoneyUseCase(
    private val walletRepository: WalletRepository, private val historyRepository: MoneyTransactionHistoryRepository
) {
    /**
     * 所持金を減らす
     *
     * @param player
     * @param amount
     * @param reason
     * @return 減少後の所持金
     */
    fun execute(player: UUID, amount: Int, reason: String? = null): Int? {
        return Database.transaction {
            val wallet = walletRepository.findBy(player) ?: return@transaction null

            val money = Money(amount)
            // お金を支払えない場合はnullを返す
            if (!wallet.canPay(money)) return@transaction null
            val moneyTransaction = wallet.pay(money, if (reason != null) Reason(reason) else null)

            walletRepository.store(wallet)
            historyRepository.store(moneyTransaction)

            return@transaction wallet.money.value
        }
    }
}
