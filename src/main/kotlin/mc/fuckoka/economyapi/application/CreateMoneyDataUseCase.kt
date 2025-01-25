package mc.fuckoka.economyapi.application

import mc.fuckoka.dbconnector.Database
import mc.fuckoka.economyapi.domain.model.Money
import mc.fuckoka.economyapi.domain.model.Wallet
import mc.fuckoka.economyapi.domain.repository.MoneyTransactionHistoryRepository
import mc.fuckoka.economyapi.domain.repository.WalletRepository
import java.sql.SQLException
import java.util.*

// バカ過ぎて適切な名称が思いつかない
class CreateMoneyDataUseCase(
    private val walletRepository: WalletRepository,
    private val historyRepository: MoneyTransactionHistoryRepository
) {
    fun execute(player: UUID, defaultMoney: Int = 0) {
        Database.transaction {
            walletRepository.store(Wallet.NewWallet(player, Money(defaultMoney)))

            val wallet = walletRepository.findBy(player) ?: throw SQLException()
            val moneyTransaction = wallet.credited(Money(defaultMoney))
            historyRepository.store(moneyTransaction)
        }
    }
}
