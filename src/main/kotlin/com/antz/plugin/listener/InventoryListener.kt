package com.antz.plugin.listener

import com.antz.plugin.gui.CustomInventoryHolder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryListener : Listener {

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val inventory = e.clickedInventory ?: return
        val customInventoryHolder = inventory.holder as? CustomInventoryHolder ?: return
        if(!customInventoryHolder.isClickable) e.isCancelled = true
        val slot = e.slot

        val slotListener = customInventoryHolder.getSlotListener(slot) ?: return
        slotListener.run(e)
    }
}