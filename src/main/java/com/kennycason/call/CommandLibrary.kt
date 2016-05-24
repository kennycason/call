package com.kennycason.call

import com.beust.klaxon.*
import com.kennycason.call.Version
import com.kennycason.call.commands.CommandRunner
import com.kennycason.call.commands.internal.StoredCommand
import java.io.File
import java.util.*

/**
 * Created by kenny on 5/18/16.
 *
 */
class CommandLibrary {
    val COMMAND_LIBRARY_FILE = File(System.getProperty("user.home"), ".call.library.json")
    val COMMAND_LIBRARY_FILE_BKP = File(System.getProperty("user.home"), ".call.library.json.bkp")

    fun all(): Map<String, StoredCommand> {
        return parse(readLibraryJson())
    }

    fun add(command: String, definition: String) {
        val library = parse(readLibraryJson())
        if (library.contains(command)) {
            println("Warning, overwriting existing command.")
        }
        if (CommandRunner().isInternalCommand(command)) {
            throw RuntimeException("Command [$command] is a reserved command name")
        }
        library[command] = StoredCommand(definition)
        save(library)
        println("command [$command] added.")
        println("\t\t$definition")
    }

    fun remove(command: String) {
        val library = parse(readLibraryJson())
        if (!library.contains(command)) {
            println("Warning, command [$command] does not exist.")
        }
        library.remove(command)
        save(library)
        println("command [$command] removed.")
    }

    fun save(library: Map<String, StoredCommand>) {
        COMMAND_LIBRARY_FILE.renameTo(COMMAND_LIBRARY_FILE_BKP)
        COMMAND_LIBRARY_FILE.createNewFile()
        COMMAND_LIBRARY_FILE.printWriter().use { out ->
            out.write(libraryToJson(library))
        }
    }

    fun parse(libraryJson: JsonObject): MutableMap<String, StoredCommand> {
        if (!libraryJson.contains("commands")) {
            throw RuntimeException("Field 'commands' was not present in library file!")
        }

        val commands = mutableMapOf<String, StoredCommand>()
        libraryJson.array<JsonObject>("commands")!!.forEach { commandObject ->
            validateCommands(commandObject)
            commands[commandObject.string("command")!!] =
                    StoredCommand(commandObject.string("definition")!!)
        }
        return commands
    }

    private fun libraryToJson(library: Map<String, StoredCommand>): String {
        val libraryJson = JsonObject()
        libraryJson.put("version", Version().version)

        val commands = ArrayList<JsonObject>()
        library.forEach { command ->
            val commandJson = JsonObject()
            commandJson["command"] = command.key
            commandJson["definition"] = command.value.definition

            commands.add(commandJson)
        }
        libraryJson["commands"] = JsonArray(commands)
        return libraryJson.toJsonString(prettyPrint = true)
    }

    private fun validateCommands(commandObject: JsonObject) {
        if (!commandObject.containsKey("command")) {
            throw RuntimeException("Field 'command' was not present in library file!")
        }
        if (!commandObject.containsKey("definition")) {
            throw RuntimeException("Field 'definition' was not present in library file!")
        }
    }

    private fun readLibraryJson(): JsonObject {
        if (!COMMAND_LIBRARY_FILE.exists()) { return buildDefaultLibraryFile() }
        try {
            return Parser().parse(COMMAND_LIBRARY_FILE.inputStream()) as JsonObject
        } catch (e : RuntimeException) {
            throw RuntimeException("Encountered error when reading [${COMMAND_LIBRARY_FILE.absoluteFile}] file: " + e.message +
                    "\nReturning empty library")
        }
    }

    private fun buildDefaultLibraryFile(): JsonObject {
        println("No Library found, generating new one, [${COMMAND_LIBRARY_FILE.absoluteFile}]")
        var libraryTemplate = "com/kennycason/call/library/template.json"
        return Parser().parse(
                Thread.currentThread()
                        .contextClassLoader
                        .getResourceAsStream(libraryTemplate)) as JsonObject
    }

}