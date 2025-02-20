package mc.fuckoka.economyapi.bukkit.listener

import mc.fuckoka.economyapi.VaultProvider
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener(private val vault: VaultProvider) : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (!vault.hasAccount(event.player)) {
            vault.createPlayerAccount(event.player)
        }
    }
}
