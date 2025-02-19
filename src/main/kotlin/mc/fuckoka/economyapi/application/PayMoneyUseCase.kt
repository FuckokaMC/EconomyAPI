package mc.fuckoka.economyapi.application

import mc.fuckoka.dbconnector.Database
import mc.fuckoka.economyapi.domain.model.Money
import mc.fuckoka.economyapi.domain.model.Reason
import mc.fuckoka.economyapi.domain.model.TransactionService
import mc.fuckoka.economyapi.domain.repository.MoneyTransactionHistoryRepository
import mc.fuckoka.economyapi.domain.repository.WalletRepository
import java.util.*

class PayMoneyUseCase(
    private val walletRepository: WalletRepository,
    private val historyRepository: MoneyTransactionHistoryRepository
) {
    /**
     * 支払う
     *
     * @param from
     * @param to
     * @param amount
     * @param reason
     * @return 支払い後の所持金
     */
    fun execute(from: UUID, to: UUID, amount: Int, reason: String? = null): Pair<Int, Int>? {
        return Database.transaction {
            val fromWallet = walletRepository.findBy(from) ?: return@transaction null
            val toWallet = walletRepository.findBy(to) ?: return@transaction null

            val moneyTransaction = TransactionService.pay(
                fromWallet,
                toWallet,
                Money(amount),
                if (reason != null) Reason(reason) else null
            ) ?: return@transaction null

            walletRepository.store(fromWallet)
            walletRepository.store(toWallet)
            historyRepository.store(moneyTransaction)

            return@transaction Pair(fromWallet.money.value, toWallet.money.value)
        }
    }
}
