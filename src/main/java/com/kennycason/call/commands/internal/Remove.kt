package com.kennycason.call.commands.internal

import com.kennycason.call.CommandLibrary
import com.kennycason.call.commands.internal.InternalCommand

/**
 * Created by kenny on 5/18/16.
 */
class Remove : InternalCommand {
    override fun run(commands: List<String>) {
        if (commands.size < 2) {
            throw RuntimeException("Must name at least one command to remove!")
        }
        commands.forEachIndexed { i, command ->
            if (i > 0) {
                CommandLibrary().remove(command)
            }
        }
    }
}