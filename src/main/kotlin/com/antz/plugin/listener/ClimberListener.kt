package com.antz.plugin.listener

import com.antz.plugin.AntRole
import com.antz.plugin.increaseProgress
import com.destroystokyo.paper.event.player.PlayerJumpEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class ClimberListener : Listener {

    @EventHandler
    fun onJump(event: PlayerJumpEvent) {
        event.player.increaseProgress(AntRole.CLIMBER)
    }

}