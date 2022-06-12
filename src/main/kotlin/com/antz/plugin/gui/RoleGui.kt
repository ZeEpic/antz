package com.antz.plugin.gui

import com.antz.plugin.AntRole
import com.antz.plugin.RoleProgress
import com.antz.plugin.antRoleProgress
import com.antz.plugin.role
import com.antz.plugin.util.formatAll
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class RoleGui : CustomInventoryHolder(27, "Antz Roles", isClickable = false) {

    override fun build() {
        applyItems()
        applyListeners()
    }

    private fun applyItems() {
        for(i in 0..26) inventory.setItem(i, GuiItems.EMPTY)
        inventory.setItem(10, GuiItems.CARPENTER_ITEM)
        inventory.setItem(11, GuiItems.CLIMBER_ITEM)
        inventory.setItem(12, GuiItems.COMPOSTER_ITEM)
        inventory.setItem(13, GuiItems.FIGHTER_ITEM)
        inventory.setItem(14, GuiItems.FIRE_ITEM)
        inventory.setItem(15, GuiItems.MINER_ITEM)
    }

    private fun applyListeners() {
        for(i in 10..15) setSlotListener(i) { e ->
            val player = e.whoClicked as Player
            val itemMeta = e.currentItem?.itemMeta
            val customAttributeModifier = (itemMeta?.attributeModifiers?.get(Attribute.GENERIC_LUCK) as List<AttributeModifier>)[0]
            val currentRole = player.role
            val selectedRole = AntRole.valueOf(customAttributeModifier.name)

            if(currentRole != null && player.antRoleProgress != null && player.antRoleProgress!!.progress < currentRole.requirement) {
                player.sendMessage("You do not have enough Progression Points to select a new role.")
                return@setSlotListener
            }

            player.role = selectedRole
            player.antRoleProgress = RoleProgress(selectedRole, player, 0)
            player.sendMessage("You have selected the ${selectedRole.toString().lowercase().capitalize()} Ant role.")
        }
    }

}

private object GuiItems {

    val EMPTY = createGuiItem(Material.GRAY_STAINED_GLASS_PANE)

    val CARPENTER_ITEM = createGuiItem(Material.SCAFFOLDING, "&fCarpenter Ant", "&fAnt Description", AntRole.CARPENTER)
    val CLIMBER_ITEM = createGuiItem(Material.LADDER, "&fClimber Ant", "&fAnt Description", AntRole.CLIMBER)
    val COMPOSTER_ITEM = createGuiItem(Material.COMPOSTER, "&fComposter Ant", "&fAnt Description", AntRole.COMPOSTER)
    val FIGHTER_ITEM = createGuiItem(Material.IRON_SWORD, "&fFighter Ant", "&fAnt Description", AntRole.FIGHTER)
    val FIRE_ITEM = createGuiItem(Material.BLAZE_POWDER, "&fFire Ant", "&fAnt Description", AntRole.FIRE)
    val MINER_ITEM = createGuiItem(Material.IRON_PICKAXE, "&fMinter Ant", "&fAnt Description", AntRole.MINER)

    private fun createGuiItem(material: Material, name: String = "", lore: String = "", role: AntRole? = null): ItemStack {
        val guiItem = ItemStack(material)

        val meta = guiItem.itemMeta
        meta.setDisplayName(name.formatAll())
        meta.lore = lore.formatAll().split("|")
        meta.addAttributeModifier(Attribute.GENERIC_LUCK, AttributeModifier(role.toString(), 0.0, AttributeModifier.Operation.ADD_NUMBER))
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        guiItem.itemMeta = meta

        return guiItem
    }

}