package com.kennycason.call

import com.kennycason.call.commands.CommandRunner

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