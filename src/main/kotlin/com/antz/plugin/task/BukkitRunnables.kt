package com.antz.plugin.task

import com.antz.plugin.Main
import org.bukkit.Bukkit


/*
    These are some functions that simplify the Bukkit scheduler system
        and take advantage of Kotlin's lambda functions
*/

fun doLater(seconds: Int, task: () -> Unit) {
    doLaterPrecise(seconds * 20L, task)
}

fun doLaterPrecise(ticks: Long, task: () -> Unit) {
    Bukkit
            .getScheduler()
            .runTaskLater(
                    Main.instance,
                    Runnable(task),
                    ticks
            )
}

fun afterTick(task: () -> Unit) {
    Bukkit
            .getScheduler()
            .runTaskLater(
                    Main.instance,
                    Runnable(task),
                    1
            )
}

fun now(task: () -> Unit) {
    Bukkit
            .getScheduler()
            .runTask(
                    Main.instance,
                    Runnable(task)
            )
}

fun <T> bukkitSuspend(task: () -> T, onComplete: (T) -> Unit = {}) {

    val scheduler = Bukkit.getScheduler()

    // Create a runnable to tell us when the task is completed
    val runnable = Runnable {
        val taskComplete = task()
        scheduler.runTask(Main.instance, Runnable { onComplete(taskComplete) })
    }

    // Run the async task
    scheduler.runTaskAsynchronously(Main.instance, runnable)

}
