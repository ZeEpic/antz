package com.antz.plugin.listener

import com.antz.plugin.AntRole
import com.antz.plugin.increaseProgress
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

class FighterListener : Listener {

    @EventHandler
    fun onEntityDeathByPlayer(event: EntityDeathEvent) {
        val entity = event.entity
        if (entity.type == EntityType.PLAYER) return
        entity.killer?.increaseProgress(AntRole.FIGHTER)
    }

}