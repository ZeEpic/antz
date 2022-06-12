package com.antz.plugin

import com.antz.plugin.commands.ResetRoleCommand
import com.antz.plugin.commands.RolesCommand
import com.antz.plugin.listener.*
import com.antz.plugin.task.ChunkGenerateTask
import com.antz.plugin.world.RegenerateCommand
import com.antz.plugin.world.WorldGenerationListener
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {
        val instance: Main
            get() = getPlugin(Main::class.java)
        val world: World by lazy { Bukkit.getWorld("world")!! }
        private val netherWorld: World by lazy { Bukkit.getWorld("world_nether")!! }
        val chunkQueue = mutableListOf<Chunk>()
        val cloneWorlds: Map<World, World> by lazy { mapOf(
            world to world.cloneWorld,
            netherWorld to netherWorld.cloneWorld
        )}
    }

    override fun onEnable() {
        // Plugin startup logic

        getCommand("regenerate")?.setExecutor(RegenerateCommand())
        getCommand("roles")?.setExecutor(RolesCommand())
        getCommand("resetrole")?.setExecutor(ResetRoleCommand())

        val manager = server.pluginManager
        manager.registerEvents(InventoryListener(), this)
        manager.registerEvents(WorldGenerationListener(), this)
        manager.registerEvents(CarpenterListener(), this)
        manager.registerEvents(ClimberListener(), this)
        manager.registerEvents(CompostListener(), this)
        manager.registerEvents(FighterListener(), this)
        manager.registerEvents(FireAntListener(), this)
        manager.registerEvents(MinerListener(), this)
        manager.registerEvents(QueenListener(), this)

        server.scheduler.runTaskTimerAsynchronously(this, ChunkGenerateTask(), 0L, 10L)

    }

    override fun onDisable() {
        // Plugin shutdown logic

        server.scheduler.cancelTasks(this)
    }

    override fun getDefaultWorldGenerator(worldName: String, id: String?) = VoidGenerator()

}

class VoidGenerator : ChunkGenerator()
