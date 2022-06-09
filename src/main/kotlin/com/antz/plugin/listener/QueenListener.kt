package com.antz.plugin.listener

import com.antz.plugin.AntRole
import com.antz.plugin.role
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class QueenListener : Listener {

    @EventHandler
    fun onPlayerDeathByPlayer(event: PlayerDeathEvent) {
        val player = event.player
        val killer = event.player.killer ?: return
        if (player.role == AntRole.QUEEN) {
            player.role = null
            killer.role = AntRole.QUEEN
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player
        if (player.role == AntRole.QUEEN) {
            player.role = null
            val players = Bukkit.getOnlinePlayers()
            if (players.isEmpty()) return
            players.random().role = AntRole.QUEEN
        }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (Bukkit.getOnlinePlayers().none { it.role == AntRole.QUEEN }) {
            player.role = AntRole.QUEEN
        }
    }

}