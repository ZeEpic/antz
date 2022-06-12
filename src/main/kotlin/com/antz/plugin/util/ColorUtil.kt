package com.antz.plugin.util

import net.md_5.bungee.api.ChatColor

val hexRegex = "&&#([A-Fa-f\\d]{6})".toRegex()

fun String.formatHEX(): String {
    return hexRegex.replace(this) {
        val value = it.groupValues[0]
        "§x§" + value.toCharArray().joinToString("§")
    }
}

fun String.formatAll(): String = ChatColor.translateAlternateColorCodes('&', this.formatHEX())