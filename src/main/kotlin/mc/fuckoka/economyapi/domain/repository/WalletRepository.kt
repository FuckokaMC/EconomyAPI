package mc.fuckoka.economyapi.domain.repository

import mc.fuckoka.economyapi.domain.model.Wallet
import mc.fuckoka.economyapi.domain.model.WalletID
import java.util.*

interface WalletRepository {
    fun find(id: WalletID): Wallet?

    fun findBy(owner: UUID): Wallet?

    fun store(wallet: Wallet)
}
