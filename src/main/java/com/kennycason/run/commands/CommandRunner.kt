package com.kennycason.run.commands

import com.kennycason.run.CommandLibrary
import com.kennycason.run.commands.internal.*
import kotlin.system.exitProcess

/**
 * Created by kenny on 5/18/16.
 */
class CommandRunner {
    val library = CommandLibrary()

    val internalCommands = mapOf(
            Pair("help", Help()),
            Pair("list", Library()),
            Pair("add", Add()),
            Pair("remove", Remove())
    )

    fun run(commands: List<String>) {
        if (commands.isEmpty()) {
            println("No command specified!\n")
            internalCommands.get("help")!!.run(emptyList())
            return
        }

        // if internal command
        if (internalCommands.containsKey(commands.first())) {
            internalCommands.get(commands.first())!!.run(commands)
            return
        }

        // assume a list of aliases
        val definitions = library.all()
        validateCommands(commands, definitions)
        runExternalCommands(commands, definitions)
    }

    private fun runExternalCommands(commands: List<String>, definitions: Map<String, ExternalCommand>) {
        commands.forEach { command ->
            definitions.get(command)!!.run(emptyList())
        }
    }

    private fun validateCommands(commands: List<String>, definitions: Map<String, ExternalCommand>) {
        commands.forEach { command ->
            if (!definitions.containsKey(command)) {
                println("Command [" + command + "] not found, try \"list\" or \"help\" to see which commands are available.")
                exitProcess(1)
            }
        }
    }
}