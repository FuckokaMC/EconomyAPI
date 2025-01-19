package mc.fuckoka.economyapi.domain.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import java.util.*

class PlayerPaidMoneyEvent(val from: UUID?, val to: UUID?, val amount: Int) : Event() {
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
