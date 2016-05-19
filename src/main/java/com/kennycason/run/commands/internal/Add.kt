package com.kennycason.run.commands.internal

import com.kennycason.run.CommandLibrary
import com.kennycason.run.commands.internal.InternalCommand

/**
 * Created by kenny on 5/18/16.
 */
class Add : InternalCommand {
    override fun run(commands: List<String>) {
        if (commands.size != 3) {
            throw RuntimeException("Must provide a command name and definition. Found " + (commands.size - 1) + " arguments. " +
                    "Be sure to add quotes around the command definition if definition contains spaces or multiple commands to run.")
        }
        val command = commands.get(1)!!
        val definition = sanitize(commands.get(2))!!
        validate(command, definition)
        CommandLibrary().add(command, definition)
    }

    private fun validate(command: String, definition: String) {
        if (command.length == 0) {
            // this shouldn't be possible
            throw RuntimeException("Command length can not be zero")
        }
        if (definition.length == 0) {
            throw RuntimeException("Command definition length can not be zero")
        }
    }

    private fun sanitize(definition: String): String {
        if ((definition.get(0) == '"' && definition.get(definition.lastIndex) == '"')
            || (definition.get(0) == '\'' && definition.get(definition.lastIndex) == '\'')) {
            return definition.substring(1, definition.lastIndex - 1)
        }
        return definition
    }
}