package com.antz.plugin.world

import com.antz.plugin.Main
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkPopulateEvent

class WorldGenerationListener : Listener {

    @EventHandler
    fun onGenerate(event: ChunkPopulateEvent) {
        val chunk = event.chunk
        if (!chunk.world.name.startsWith("world")) return
        if (chunk in Main.chunkQueue) return
        if (chunk.z != 0) return
        Main.chunkQueue += chunk
    }

}