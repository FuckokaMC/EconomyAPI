package mc.fuckoka.economyapi.application

import mc.fuckoka.dbconnector.Database
import mc.fuckoka.economyapi.domain.repository.WalletRepository
import java.util.*

class FindMoneyUseCase(private val walletRepository: WalletRepository) {
    fun execute(player: UUID): Int? {
        return Database.transaction {
            val wallet = walletRepository.findBy(player) ?: return@transaction null
            return@transaction wallet.money.value
        }
    }
}
