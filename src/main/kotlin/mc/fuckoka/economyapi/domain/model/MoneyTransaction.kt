package mc.fuckoka.economyapi.domain.model

import java.time.LocalDateTime

data class MoneyTransaction(
    val id: Long?,
    val from: WalletID?,
    val to: WalletID?,
    val amount: Money,
    val datetime: LocalDateTime = LocalDateTime.now(),
    val reason: Reason? = null
) {
    init {
        // 単にお金を増やす場合もある(givemoneyコマンドとか)ためどちらもnullableだが、どちらかはnullでないことが条件
        require(from != null || to != null)
    }
}
