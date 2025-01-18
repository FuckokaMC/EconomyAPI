package mc.fuckoka.economyapi.model

import java.util.*

class Wallet(private val owner: UUID, private var money: Money) {
    /**
     * 支払う
     *
     * @param amount
     */
    fun pay(amount: Money) {
        money = Money(money.value - amount.value)
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
