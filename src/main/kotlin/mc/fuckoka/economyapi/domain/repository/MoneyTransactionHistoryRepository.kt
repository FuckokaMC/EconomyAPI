package mc.fuckoka.economyapi.domain.repository

import mc.fuckoka.economyapi.domain.model.MoneyTransaction
import mc.fuckoka.economyapi.domain.model.Wallet
import mc.fuckoka.economyapi.domain.model.WalletID

interface MoneyTransactionHistoryRepository {
    /**
     * 履歴取得
     *
     * @param wallet
     * @param start  初期レコード位置
     * @param offset 取得件数
     * @return
     */
    fun find(wallet: Wallet, start: Int? = null, offset: Int? = null): List<MoneyTransaction>

    fun count(id: WalletID): Int

    fun store(event: MoneyTransaction)
}
