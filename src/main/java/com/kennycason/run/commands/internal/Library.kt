package com.kennycason.run.commands.internal

import com.kennycason.run.CommandLibrary
import com.kennycason.run.commands.internal.InternalCommand

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