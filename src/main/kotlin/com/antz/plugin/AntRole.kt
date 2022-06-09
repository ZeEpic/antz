package com.antz.plugin

import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.potion.PotionEffectType.*

fun Player.increaseProgress(role: AntRole) {
    val roleProgress = this.antRoleProgress
    if (roleProgress?.futureRole != role) return
    roleProgress.progress++
    if (this.antRoleProgress == null) return // This will happen if the future role goal is completed
    this.antRoleProgress = roleProgress
}

fun PotionEffectType.infiniteEffect(amplifier: Int = 1) = PotionEffect(this, 1_000_000, amplifier, false, false, true)

class RoleProgress(val futureRole: AntRole, private val player: Player, progress: Int) {
    var progress: Int = progress
        set(value) {
            if (value >= futureRole.requirement) {
                player.role = futureRole
                player.antRoleProgress = null
            }
            field = value
        }
}

// [currentRole: AntRole?, futureRole: AntRole?, progress: Int]
val antRoleKey = NamespacedKey(Main.instance, "role")

var Player.antRoleProgress: RoleProgress?
    get() {
        if (!persistentDataContainer.has(antRoleKey)) return null
        val numbers = persistentDataContainer.get(antRoleKey, PersistentDataType.INTEGER_ARRAY) ?: return null
        return RoleProgress(AntRole.values().getOrNull(numbers[1]) ?: return null, this, numbers[2])
    }
    set(value) {
        persistentDataContainer.set(
                antRoleKey,
                PersistentDataType.INTEGER_ARRAY,
                intArrayOf(role?.ordinal ?: -1, value?.futureRole?.ordinal ?: -1, value?.progress ?: 0)
        )
    }

var Player.role: AntRole?
    get() {
        if (!persistentDataContainer.has(antRoleKey)) return null
        val ordinal = persistentDataContainer.get(antRoleKey, PersistentDataType.INTEGER_ARRAY)?.get(0) ?: return null
        if (ordinal < 0) return null
        return AntRole.values()[ordinal]
    }
    set(value) {
        this.role?.effects?.map { it.type }?.forEach(this::removePotionEffect)
        value?.effects?.let { this.addPotionEffects(it) }
        val previousData = this.antRoleProgress
        persistentDataContainer.set(
                antRoleKey,
                PersistentDataType.INTEGER_ARRAY,
                intArrayOf(value?.ordinal ?: -1, previousData?.futureRole?.ordinal ?: -1, previousData?.progress ?: 0)
        )
    }

enum class AntRole(val effects: List<PotionEffect>, val requirement: Int) {
    CARPENTER(listOf(SPEED.infiniteEffect()), 500), // Place blocks
    MINER(listOf(FAST_DIGGING.infiniteEffect()), 400), // Break blocks
    FIGHTER(listOf(INCREASE_DAMAGE.infiniteEffect(0)), 150), // Kill mobs
    FIRE(listOf(FIRE_RESISTANCE.infiniteEffect(4)), 175), // Take fire damage
    CLIMBER(listOf(JUMP.infiniteEffect()), 700), // Jump
    COMPOSTER(emptyList(), 60), // Compost items
    HEALTHY(listOf(HEALTH_BOOST.infiniteEffect()), 12), // Drink health potions
    QUEEN(listOf(GLOWING.infiniteEffect(0), DAMAGE_RESISTANCE.infiniteEffect()), -1) // Kill the queen, or join when no one else is online
}