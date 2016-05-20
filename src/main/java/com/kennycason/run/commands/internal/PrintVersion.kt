package com.kennycason.run.commands.internal

import com.kennycason.run.Version
import com.kennycason.run.commands.internal.InternalCommand

/**
 * Created by kenny on 5/19/16.
 */
class PrintVersion : InternalCommand {
    override fun run(command : List<String>) {
        println("Run version " + Version().version)
    }
}