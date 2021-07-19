package me.kansio.indium.listeners

import me.kansio.indium.IndiumPlugin
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class FreezeListener : Listener {

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        var player = event.player
        if (IndiumPlugin.getInstance().freezeManager.isFrozen(player)) {
            player.teleport(event.from)
            player.sendMessage("§cYou're frozen, please join ts.zonix.us.")
        }
    }

}