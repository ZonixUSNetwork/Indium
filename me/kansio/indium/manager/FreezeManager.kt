package me.kansio.indium.manager

import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.ArrayList

class FreezeManager {

    private val frozen: ArrayList<UUID> = ArrayList()

    fun isFrozen(player: Player): Boolean {
        return frozen.contains(player.uniqueId)
    }

    fun addFrozen(player: Player) {
        frozen.add(player.uniqueId)
    }

    fun removeFrozen(player: Player) {
        frozen.remove(player.uniqueId)
    }

    /*fun setFiltered() {
        filtered.add("nigger")
        filtered.add("fag")
        filtered.add("kys")
        filtered.add("kill urself")
        filtered.add("kill yourself")
    }*/
}