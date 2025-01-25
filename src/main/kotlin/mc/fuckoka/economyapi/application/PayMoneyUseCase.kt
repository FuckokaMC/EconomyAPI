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
    fun execute(from: UUID, to: UUID, amount: Int, reason: String? = null): Boolean {
        return Database.transaction {
            val fromWallet = walletRepository.findBy(from) ?: return@transaction false
            val toWallet = walletRepository.findBy(to) ?: return@transaction false

            val moneyTransaction = TransactionService.pay(
                fromWallet,
                toWallet,
                Money(amount),
                if (reason != null) Reason(reason) else null
            ) ?: return@transaction false

            historyRepository.store(moneyTransaction)
            walletRepository.store(fromWallet)
            walletRepository.store(toWallet)

            return@transaction true
        }
    }
}
