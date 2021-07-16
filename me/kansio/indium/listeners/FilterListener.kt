package me.kansio.indium.listeners

import cc.fyre.venom.VenomAPI
import me.kansio.indium.IndiumPlugin
import me.kansio.indium.redis.Payload
import me.kansio.indium.utils.JsonBuilder
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerChatEvent
import org.bukkit.event.player.PlayerChatTabCompleteEvent

class FilterListener : Listener {

    @EventHandler
    fun onPlayerChat(event: PlayerChatEvent) {
        val message = event.message
        val player = event.player
        if (IndiumPlugin.getInstance().filterManager.isFiltered(message)) {
            IndiumPlugin.getInstance().publisher.write(Payload.FILTER, JsonBuilder()
                .add("server", IndiumPlugin.getInstance().serverName)
                .add("uuid", player.uniqueId)
                .add("message", message)
                .add("sender", VenomAPI.instance.grantHandler.findBestRank(player.uniqueId).color.replace("&", "§") + player.name)
                .build())
            event.isCancelled = true
        }
    }

}