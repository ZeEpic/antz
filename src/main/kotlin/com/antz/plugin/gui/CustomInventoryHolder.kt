package com.antz.plugin.gui

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

abstract class CustomInventoryHolder : InventoryHolder {

    private var inv: Inventory
    lateinit var slotListeners: Array<SlotListener?>
    var isClickable = false

    constructor(size: Int, title: String, isClickable: Boolean) {
        inv = Bukkit.createInventory(this, size, title)
        this.isClickable = isClickable
        lateInit()
    }

    constructor(type: InventoryType, title: String, isClickable: Boolean) {
        inv = Bukkit.createInventory(this, type, title)
        this.isClickable = isClickable
        lateInit()
    }

    private fun lateInit() {
        slotListeners = Array(inv.size) { null }
        build()
    }

    abstract fun build()

    fun setSlotListener(slot: Int, listener: SlotListener) { slotListeners[slot] = listener }
    fun getSlotListener(slot: Int): SlotListener? = slotListeners[slot]

    override fun getInventory(): Inventory = inv
}

fun interface SlotListener {
    fun run(e: InventoryClickEvent)
}