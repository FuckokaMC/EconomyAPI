package mc.fuckoka.economyapi.domain.model

object TransactionService {
    /**
     * 支払い
     */
    fun pay(from: Player, to: Player, amount: Money, reason: Reason? = null): MoneyTransaction? {
        if (!from.canPay(amount) || !to.canCredited(amount)) return null
        from.pay(amount)
        to.credited(amount)
        return MoneyTransaction(null, from, to, amount, reason)
    }
}
