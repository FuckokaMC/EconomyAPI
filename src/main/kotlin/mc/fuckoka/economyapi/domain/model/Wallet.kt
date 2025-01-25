package mc.fuckoka.economyapi.domain.model

import java.util.*

open class Wallet(val id: WalletID, val owner: UUID, money: Money) {
    var money = money
        private set

    /**
     * 支払えるか
     */
    fun canPay(amount: Money): Boolean {
        return Money.validate(money.value - amount.value)
    }

    /**
     * 支払い
     */
    fun pay(amount: Money, reason: Reason? = null): MoneyTransaction {
        money = Money(money.value - amount.value)
        return MoneyTransaction(null, this, null, amount, reason)
    }

    /**
     * 受け取れるか
     */
    fun canCredited(amount: Money): Boolean {
        // お金を最大値までためると取引できなくなるみたいなパターンを防ぐため。
        // 問題としては支払う側は支払って受け取る側は受け取ってないみたいな(要するにお金を捨てた)状態になり、
        // サーバー全体の資産を見たときに資産が単に減ってしまうだけになってしまうが、今回はそんなに厳密にはしないためこれでヨシ
        // return Money.validate(money.value + amount.value)
        return true
    }

    /**
     * 受取
     */
    fun credited(amount: Money, reason: Reason? = null): MoneyTransaction {
        money = kotlin.runCatching { Money(money.value + amount.value) }.getOrElse { Money(Money.MAX_VALUE) }
        return MoneyTransaction(null, null, this, amount, reason)
    }

    class NewWallet(owner: UUID, money: Money) : Wallet(WalletID(0), owner, money)
}
