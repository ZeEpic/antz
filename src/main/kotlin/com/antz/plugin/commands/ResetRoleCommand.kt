package com.antz.plugin.commands

import com.antz.plugin.antRoleProgress
import com.antz.plugin.role
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ResetRoleCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player: Player = sender as? Player ?: sender.sendMessage("Only players can run this command.").run { return false }
        player.role = null
        player.antRoleProgress = null
        player.sendMessage("Role successfully reset.")
        return true
    }
}