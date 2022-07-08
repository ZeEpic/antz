package com.antz.plugin.world

import com.antz.plugin.Main
import com.antz.plugin.cloneWorld
import org.bukkit.Chunk
import org.bukkit.ChunkSnapshot
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

private fun ChunkSnapshot.fillHeight(x: Int, z: Int, chunk: Chunk, barriers: Boolean) {
    if (barriers) {
        for (y in -64..317) {
            chunk.getBlock(x, y, z).type = Material.BARRIER
        }
    } else {
        for (y in -64..317) {
            val block = chunk.getBlock(x, y, z)
            block.blockData = this.getBlockData(x, y, z)
            block.biome = this.getBiome(x, y, z)
        }
    }
    chunk.getBlock(x, 318, z).type = Material.BARRIER
    chunk.getBlock(x, 319, z).type = Material.BARRIER
}

fun ChunkSnapshot.fillChunk(chunk: Chunk) {
    for (x in 0..15) {
        for (z in 0..1) {
            fillHeight(x, z, chunk, true)
        }
        for (z in 2..13) {
            fillHeight(x, z, chunk, false)
        }
        for (z in 14..15) {
            fillHeight(x, z, chunk, true)
        }
    }
}

class RegenerateCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val chunkX = args.getOrNull(0) ?: return false
        val chunkZ = args.getOrNull(1) ?: return false
        val player = sender as? Player
        val playerChunk = player?.location?.chunk
        val x = chunkX.toIntOrNull() ?: if (chunkX == "~") playerChunk?.x else return false
        val z = chunkZ.toIntOrNull() ?: if (chunkZ == "~") playerChunk?.z else return false
        if (x == null || z == null) return false // "~ ~" has no meaning when used in console
        val chunk = Main.world.cloneWorld.getChunkAt(x, z)
        val chunkDestination = Main.world.getChunkAt(x, z)
        val snapshot = chunk.getChunkSnapshot(true, true, true)
        snapshot.fillChunk(chunkDestination)
        return true
    }
}