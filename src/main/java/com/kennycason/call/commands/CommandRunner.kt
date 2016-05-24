package com.kennycason.call.commands

import com.kennycason.call.CommandLibrary
import com.kennycason.call.commands.internal.*
import kotlin.system.exitProcess

/**
 * Created by kenny on 5/18/16.
 */
class CommandRunner {
    val library = CommandLibrary()

    val internalCommands = mapOf(
            Pair("add", Add()),
            Pair("help", Help()),
            Pair("list", Library()),
            Pair("version", PrintVersion()),
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
        val library = library.all()
        runExternalCommands(commands, library)
    }

    fun isInternalCommand(command: String): Boolean {
        return internalCommands.contains(command)
    }

    private fun runExternalCommands(commandAndArguments: List<String>, library: Map<String, StoredCommand>) {
        val command = commandAndArguments.first()
        val arguments = if (commandAndArguments.size == 1) {
            emptyList<String>()
        } else {
            commandAndArguments.subList(1, commandAndArguments.size)
        }
        validateCommand(command, library)
        library.get(command)!!.run(arguments)
    }

    private fun validateCommand(command: String, library: Map<String, StoredCommand>) {
        if (!library.containsKey(command)) {
            println("Command [$command] not found, try \"list\" or \"help\" to see which commands are available.")
            exitProcess(1)
        }
    }
}