package mc.fuckoka.economyapi.application

import mc.fuckoka.dbconnector.Database
import mc.fuckoka.economyapi.domain.model.MoneyTransaction
import mc.fuckoka.economyapi.domain.repository.MoneyTransactionHistoryRepository
import mc.fuckoka.economyapi.domain.repository.WalletRepository
import java.util.*

// ログ検索
class FindHistoriesUseCase(
    private val walletRepository: WalletRepository,
    private val historyRepository: MoneyTransactionHistoryRepository
) {
    /**
     * @param player
     * @param start
     * @param offset
     * @return <履歴, 全件数>
     */
    fun execute(player: UUID, start: Int? = null, offset: Int? = null): Pair<List<MoneyTransaction>, Int> {
        return Database.transaction {
            val connection = Database.connection

            val wallet = walletRepository.findBy(player) ?: return@transaction Pair(listOf<MoneyTransaction>(), 0)
            val moneyTransactions = historyRepository.find(wallet.id, start, offset)
            val count = historyRepository.count(wallet.id)

            return@transaction Pair(moneyTransactions, count)
        }
    }
}
