package mc.fuckoka.economyapi.application

import mc.fuckoka.dbconnector.Database
import mc.fuckoka.economyapi.domain.model.Money
import mc.fuckoka.economyapi.domain.model.Wallet
import mc.fuckoka.economyapi.domain.repository.MoneyTransactionHistoryRepository
import mc.fuckoka.economyapi.domain.repository.WalletRepository
import java.util.*

class CreateWalletUseCase(
    private val walletRepository: WalletRepository,
    private val historyRepository: MoneyTransactionHistoryRepository
) {
    fun execute(player: UUID, defaultMoney: Int = 0): Boolean {
        return Database.transaction {
            if (walletRepository.findBy(player) != null) return@transaction false

            // wallet作成
            walletRepository.store(Wallet.NewWallet(player))

            // 初期所持金を与える
            val wallet = walletRepository.findBy(player) ?: throw RuntimeException()
            val moneyTransaction = wallet.credited(Money(defaultMoney))

            // 履歴とwallet自体の保存
            walletRepository.store(wallet)
            historyRepository.store(moneyTransaction)

            return@transaction true
        }
    }
}
