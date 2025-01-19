package mc.fuckoka.economyapi.application

import mc.fuckoka.economyapi.config.Database
import mc.fuckoka.economyapi.domain.model.Money
import mc.fuckoka.economyapi.domain.model.Player
import mc.fuckoka.economyapi.domain.repository.MoneyTransactionHistoryRepository
import java.util.*

// TODO:バカ過ぎて適切な名称が思いつかない
class CreateMoneyDataUseCase(private val historyRepository: MoneyTransactionHistoryRepository) {
    fun execute(player: UUID, defaultMoney: Int = 0) {
        val model = Player(player, Money(0))
        val moneyTransaction = model.credited(Money(defaultMoney))

        Database.transaction(Database.connect()) {
            historyRepository.store(moneyTransaction)
        }
    }
}
