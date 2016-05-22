package com.kennycason.run

import com.kennycason.run.commands.CommandRunner

/**
 * Created by kenny on 5/18/16.
 */
fun main(commands : Array<String>) {
    try {
        CommandRunner().run(commands.asList())
    } catch (e: RuntimeException) {
        println(e.message)
        //e.printStackTrace()
    }
}