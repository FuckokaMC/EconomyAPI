package mc.fuckoka.economyapi.domain.model

import java.time.LocalDateTime

open class MoneyTransaction(
    val id: Int,
    val from: Wallet?,
    val to: Wallet?,
    val amount: Money,
    val datetime: LocalDateTime,
    val reason: Reason?
) {
    init {
        // 単にお金を増やす場合もある(givemoneyコマンドとか)ためどちらもnullableだが、どちらかはnullでないことが条件
        require(from != null || to != null)
    }

    class NewMoneyTransaction(from: Wallet?, to: Wallet?, amount: Money, reason: Reason? = null) : MoneyTransaction(
        0, from, to, amount, LocalDateTime.now(), reason
    )
}
