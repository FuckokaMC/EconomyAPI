package mc.fuckoka.economyapi.infrastructure

import mc.fuckoka.economyapi.domain.model.Wallet
import mc.fuckoka.economyapi.domain.model.WalletID
import java.util.*

class SimpleWalletRepositoryImpl : WalletRepositoryImpl() {
    override fun find(id: WalletID): Wallet? {
        TODO("Not yet implemented")
    }

    override fun findBy(owner: UUID): Wallet? {
        TODO("Not yet implemented")
    }
}
