package com.kennycason.call.commands.internal

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.kennycason.call.CommandLibrary
import com.kennycason.call.commands.internal.InternalCommand
import java.io.File
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by kenny on 5/18/16.
 */
class Add : InternalCommand {
    override fun run(commands: List<String>) {
        if (commands.size == 2) {
            addCommandsForResource(commands.get(1))
        }
        else if (commands.size == 3) {
            val command = commands.get(1)!!
            val definition = sanitize(commands.get(2))!!
            addSingleCommand(command, definition)
        }
        else {
            throw RuntimeException("Must provide a command name and definition. Found " + (commands.size - 1) + " arguments. " +
                    "Be sure to add quotes around the command definition if definition contains spaces or multiple commands to call.")
        }
    }

    private fun addSingleCommand(command: String, definition: String) {
        validate(command, definition)
        CommandLibrary().add(command, definition)
    }

    private fun addCommandsForResource(resource: String) {
        val commandLibrary = CommandLibrary()
        val commandsToImport = commandLibrary.parse(loadResource(resource))
        commandsToImport.forEach { command ->
            println("Importing command: " + command.key + "\n\t\t" + command.value.definition)
            addSingleCommand(command.key, command.value.definition)
        }
    }

    private fun loadResource(resource: String): JsonObject {
        if (isUrlValid(resource)) {
            println("Importing library from file: " + resource)
            return Parser().parse(URL(resource).openStream()) as JsonObject
        }
        else if (File(resource).exists()) {
            println("Importing library from file: " + resource)
            return Parser().parse(resource) as JsonObject
        }
        throw RuntimeException("Library file or url could not be found!")
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

    private fun isUrlValid(url: String): Boolean {
        try {
            URL(url)
            return true;
        } catch (e: MalformedURLException) {
            return false;
        }
    }
}