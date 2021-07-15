package me.kansio.indium.utils

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

object UUIDUtils {

    fun getPlayer(uuid: UUID): Player {
        return Bukkit.getPlayer(uuid)
    }

}