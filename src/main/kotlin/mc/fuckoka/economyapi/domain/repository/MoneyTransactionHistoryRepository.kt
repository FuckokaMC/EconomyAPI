package mc.fuckoka.economyapi.domain.repository

import mc.fuckoka.economyapi.domain.model.MoneyTransaction
import java.util.*

interface MoneyTransactionHistoryRepository {
    fun findBy(player: UUID): List<MoneyTransaction>

    fun store(event: MoneyTransaction)
}
