package me.kansio.indium.manager

import org.bukkit.Bukkit
import java.util.*
import kotlin.collections.ArrayList

class FilterManager {

    private val filtered: Array<String> = arrayOf(
        "nigger",
        "fag",
        "kys",
        "kill urself",
        "kill yourself"
    
    )

    fun isFiltered(message: String): Boolean {
        var wasFiltered = false
        for (string in filtered) {
            wasFiltered = true
        }
        return wasFiltered
    }

    /*fun setFiltered() {
        filtered.add("nigger")
        filtered.add("fag")
        filtered.add("kys")
        filtered.add("kill urself")
        filtered.add("kill yourself")
    }*/
}