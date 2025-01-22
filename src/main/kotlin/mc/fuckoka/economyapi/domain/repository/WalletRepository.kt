package mc.fuckoka.economyapi.domain.repository

import mc.fuckoka.economyapi.domain.model.Wallet
import java.util.*

interface WalletRepository {
    fun find(id: UUID): Wallet
}
