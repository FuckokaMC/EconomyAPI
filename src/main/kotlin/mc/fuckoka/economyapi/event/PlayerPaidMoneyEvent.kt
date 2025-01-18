package mc.fuckoka.economyapi.event

import mc.fuckoka.economyapi.model.Money
import mc.fuckoka.economyapi.model.Wallet
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlayerPaidMoneyEvent(val from: Wallet?, val to: Wallet?, val amount: Money) : Event() {
    companion object {
        private val handlers = HandlerList()

        fun getHandlerList(): HandlerList {
            return handlers
        }
    }

    init {
        // どちらかは必ず存在すること
        require(from != null || to != null)
    }

    override fun getHandlers(): HandlerList = handlers
}
