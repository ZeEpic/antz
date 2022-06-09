package com.antz.plugin.listener

import com.antz.plugin.AntRole
import com.antz.plugin.increaseProgress
import org.bukkit.Material
import org.bukkit.block.data.Levelled
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class CompostListener : Listener {

    @EventHandler
    fun onCompost(event: PlayerInteractEvent) {
        val player = event.player
        val block = event.clickedBlock
        if (block?.type != Material.COMPOSTER) return
        val data = block.blockData as? Levelled ?: return
        // TODO: Custom composter stuff goes here
        // TODO: Check if the level increased from the interaction and increase composter role progress
        //       event.player.increaseProgress(AntRole.COMPOSTER)
    }

}