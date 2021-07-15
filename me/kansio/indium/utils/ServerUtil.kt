package me.kansio.indium.utils

import org.bukkit.entity.Player
import org.bukkit.Bukkit

object ServerUtil {

    fun sendToStaff(message: String?) {
        for (player in Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission("rank.staff")) {
                return
            }
            player.sendMessage(message)
        }
    }
}