package mc.fuckoka.economyapi.domain.model

data class MoneyTransaction(val id: Long?, val from: Wallet?, val to: Wallet?, val amount: Money, val reason: Reason?) {
    init {
        // 単にお金を増やす場合もある(givemoneyコマンドとか)ためどちらもnullableだが、どちらかはnullでないことが条件
        require(from != null || to != null)
    }
}
