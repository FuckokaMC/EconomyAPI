package mc.fuckoka.economyapi.application

import mc.fuckoka.dbconnector.Database
import mc.fuckoka.economyapi.domain.model.Money
import mc.fuckoka.economyapi.domain.model.Reason
import mc.fuckoka.economyapi.domain.repository.MoneyTransactionHistoryRepository
import mc.fuckoka.economyapi.domain.repository.WalletRepository
import java.util.UUID

class TakeMoneyUseCase(
    private val walletRepository: WalletRepository,
    private val historyRepository: MoneyTransactionHistoryRepository
) {
    fun execute(player: UUID, amount: Int, reason: String? = null): Boolean {
        return Database.transaction {
            val wallet = walletRepository.findBy(player) ?: return@transaction false

            val money = Money(amount)
            // お金を支払えない場合はfalseを返す
            if (!wallet.canPay(money)) return@transaction false
            val moneyTransaction = wallet.pay(money, if (reason != null) Reason(reason) else null)

            historyRepository.store(moneyTransaction)
            walletRepository.store(wallet)

            return@transaction true
        }
    }
}
