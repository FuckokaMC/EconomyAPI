package mc.fuckoka.economyapi.domain.repository

import mc.fuckoka.economyapi.domain.model.MoneyTransaction
import mc.fuckoka.economyapi.domain.model.WalletID
import java.util.*

interface MoneyTransactionHistoryRepository {
    fun find(id: WalletID): List<MoneyTransaction>

    fun findBy(owner: UUID): List<MoneyTransaction>

    fun store(event: MoneyTransaction)
}
