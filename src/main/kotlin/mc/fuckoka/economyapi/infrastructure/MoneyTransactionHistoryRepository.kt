package mc.fuckoka.economyapi.infrastructure

import mc.fuckoka.economyapi.domain.event.PlayerPaidMoneyEvent
import java.util.UUID

interface MoneyTransactionHistoryRepository {
    fun findBy(player: UUID): List<PlayerPaidMoneyEvent>

    fun store(event: PlayerPaidMoneyEvent)
}
