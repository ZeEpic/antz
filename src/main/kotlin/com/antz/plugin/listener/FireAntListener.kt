package com.antz.plugin.listener

import com.antz.plugin.AntRole
import com.antz.plugin.increaseProgress
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByBlockEvent
import org.bukkit.event.entity.EntityDamageEvent

class FireAntListener : Listener {

    @EventHandler
    fun onPlayerDamaged(event: EntityDamageByBlockEvent) {
        val player = event.entity as? Player ?: return
        val block = event.damager?.type ?: return
        val isFireBlock = block == Material.FIRE || block == Material.LAVA || block == Material.SOUL_FIRE || block == Material.MAGMA_BLOCK
        val isFireCause = event.cause == EntityDamageEvent.DamageCause.FIRE || event.cause == EntityDamageEvent.DamageCause.FIRE_TICK
        if (isFireBlock && isFireCause) {
            player.increaseProgress(AntRole.FIRE)
        }
    }

}