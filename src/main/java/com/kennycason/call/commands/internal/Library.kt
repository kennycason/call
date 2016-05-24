package com.kennycason.call.commands.internal

import com.kennycason.call.CommandLibrary
import com.kennycason.call.commands.internal.InternalCommand

/**
 * Created by kenny on 5/18/16.
 */
class Library : InternalCommand {
    override fun run(command : List<String>) {
        val library = CommandLibrary().all()
        library.forEach { command ->
            println(command.key)
            println("\t\t" + command.value.definition)
            println()
        }
    }
}