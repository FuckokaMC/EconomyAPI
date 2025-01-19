package mc.fuckoka.economyapi.infrastructure

import mc.fuckoka.economyapi.domain.model.Wallet
import java.util.UUID

interface WalletRepository {
    fun find(owner: UUID): Wallet
}
