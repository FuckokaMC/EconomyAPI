package mc.fuckoka.economyapi.model

import java.util.*

class Wallet(val owner: UUID, private var money: Money) {
    /**
     * 支払い可能か
     *
     * @param amount
     * @return
     */
    fun canPay(amount: Money): Boolean {
        return kotlin.runCatching {
            Money(money.value - amount.value)
        }.isSuccess
    }

    /**
     * 支払う
     *
     * @param amount
     */
    fun pay(amount: Money) {
        money = Money(money.value - amount.value)
    }

    /**
     * 受取可能か
     *
     * @param amount
     * @return
     */
    fun canCredited(amount: Money): Boolean {
        return kotlin.runCatching {
            Money(money.value + amount.value)
        }.isSuccess
    }

    /**
     * 受け取る
     *
     * @param amount
     */
    fun credited(amount: Money) {
        money = Money(money.value + amount.value)
    }
}
