package me.kansio.indium.listeners

import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.entity.Player
import me.kansio.indium.IndiumPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class StaffChatListener : Listener {

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        val player = event.player

        if (!player.hasPermission("rank.staff")) {
            return
        }
        if (!IndiumPlugin.getInstance().staffChatManager.isToggled(player)) {
            return
        }

        event.isCancelled = true;
        player.chat("/sc " + event.message)
    }

    //remove them from the hashmap
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        if (IndiumPlugin.getInstance().staffChatManager.isToggled(player)) {
            IndiumPlugin.getInstance().staffChatManager.disable(player)
        }

    }
}