package com.antz.plugin.task

import com.antz.plugin.Main
import com.antz.plugin.world.fillChunk
import org.bukkit.Chunk
import org.bukkit.World
import java.util.function.Consumer

private fun World.getChunkAsync(chunk: Chunk, task: (Chunk) -> Unit) {
    this.getChunkAtAsync(chunk.x, chunk.z, Consumer(task))
}

class ChunkGenerateTask : Runnable {

    override fun run() {
        val destinationChunk = Main.chunkQueue.getOrNull(0) ?: return
        Main.chunkQueue -= destinationChunk
        Main.cloneWorlds[destinationChunk.world]!!.getChunkAsync(destinationChunk) {
            val snapshot = it.getChunkSnapshot(true, true, true)
            snapshot.fillChunk(destinationChunk) // If it's still laggy, this is why
        }
    }

}