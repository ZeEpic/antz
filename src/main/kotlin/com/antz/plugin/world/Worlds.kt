package com.antz.plugin

import org.bukkit.Bukkit
import org.bukkit.World

val World.cloneWorld: World
    get() = Bukkit.getWorld("clone$name")!!