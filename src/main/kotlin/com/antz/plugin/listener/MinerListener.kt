package com.antz.plugin.listener

import com.antz.plugin.AntRole
import com.antz.plugin.increaseProgress
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class MinerListener : Listener {

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        event.player.increaseProgress(AntRole.MINER)
    }

}