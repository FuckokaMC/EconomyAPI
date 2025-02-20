package mc.fuckoka.economyapi.domain.repository

import mc.fuckoka.economyapi.domain.model.MoneyTransaction
import mc.fuckoka.economyapi.domain.model.WalletID

interface MoneyTransactionHistoryRepository {
    /**
     * 履歴取得
     *
     * @param id
     * @param start  初期レコード位置
     * @param offset 取得件数
     * @return
     */
    fun find(id: WalletID, start: Long? = null, offset: Long? = null): List<MoneyTransaction>

    fun count(id: WalletID): Long

    fun store(event: MoneyTransaction)
}
