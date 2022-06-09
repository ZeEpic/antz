package com.antz.plugin.listener

import com.antz.plugin.AntRole
import com.antz.plugin.increaseProgress
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class CarpenterListener : Listener {

    @EventHandler
    fun onPlace(event: BlockPlaceEvent) {
        event.player.increaseProgress(AntRole.CARPENTER)
    }

}