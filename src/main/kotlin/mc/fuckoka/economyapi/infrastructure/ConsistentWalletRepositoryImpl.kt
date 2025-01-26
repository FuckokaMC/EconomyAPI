package mc.fuckoka.economyapi.infrastructure

import mc.fuckoka.economyapi.domain.model.Wallet
import mc.fuckoka.economyapi.domain.model.WalletID
import mc.fuckoka.economyapi.domain.repository.WalletRepository
import java.util.*

class ConsistentWalletRepositoryImpl : WalletRepository {
    override fun find(id: WalletID): Wallet? {
        TODO("Not yet implemented")
    }

    override fun findBy(owner: UUID): Wallet? {
        TODO("Not yet implemented")
    }

    override fun store(wallet: Wallet) {
        TODO("Not yet implemented")
    }
}
