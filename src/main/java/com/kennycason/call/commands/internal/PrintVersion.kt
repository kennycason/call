package com.kennycason.call.commands.internal

import com.kennycason.call.Version
import com.kennycason.call.commands.internal.InternalCommand

/**
 * Created by kenny on 5/19/16.
 */
class PrintVersion : InternalCommand {
    override fun run(command : List<String>) {
        println("Run version " + Version().version)
    }
}