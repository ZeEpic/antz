package com.antz.plugin.commands

import com.antz.plugin.gui.RoleGui
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RolesCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player: Player = sender as? Player ?: sender.sendMessage("Only players can run this command.").run { return false }
        val roleGui = RoleGui()
        player.openInventory(roleGui.inventory)
        return true
    }
}