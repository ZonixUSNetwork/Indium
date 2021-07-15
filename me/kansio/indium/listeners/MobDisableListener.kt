package me.kansio.indium.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent

class MobDisableListener : Listener {

    @EventHandler
    fun onMobSpawn(event: EntitySpawnEvent) {
        event.isCancelled = true
    }
}