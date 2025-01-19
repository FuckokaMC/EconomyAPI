package mc.fuckoka.economyapi.domain.model

import mc.fuckoka.economyapi.domain.event.PlayerPaidMoneyEvent

object PaymentService {
    /**
     * 支払い
     *
     * @param from 支払い元
     * @param to   支払い先
     * @param amount
     * @return
     */
    fun pay(from: Wallet, to: Wallet, amount: Money): PlayerPaidMoneyEvent? {
        if (!from.canPay(amount) || !to.canCredited(amount)) return null
        from.pay(amount)
        to.pay(amount)
        return PlayerPaidMoneyEvent(from.owner, to.owner, amount.value)
    }
}
