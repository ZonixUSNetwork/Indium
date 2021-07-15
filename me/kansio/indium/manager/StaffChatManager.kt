package me.kansio.indium.manager

import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class StaffChatManager {

    private val toggled: ArrayList<UUID> = ArrayList()

    fun enable(player: Player) {
        toggled.add(player.uniqueId)
    }

    fun isToggled(player: Player): Boolean {
        return toggled.contains(player.uniqueId)
    }

    fun disable(player: Player) {
        toggled.remove(player.uniqueId)
    }

    fun toggle(player: Player) {
        if (isToggled(player))
            disable(player)
        else
            enable(player)
    }

}