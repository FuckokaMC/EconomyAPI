package mc.fuckoka.economyapi.domain.repository

import mc.fuckoka.economyapi.domain.model.Player
import java.util.*

interface PlayerRepository {
    fun find(id: UUID): Player
}
