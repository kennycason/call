package com.kennycason.run.commands.internal

import com.kennycason.run.CommandLibrary
import java.io.*
import java.lang.instrument.ClassDefinition

/**
 * Created by kenny on 5/18/16.
 *
 * Due to many random issues with running arbitrary raw commands in java, this command creates a temporary
 * shell script, copies all commands to it, and runs the script.
 */
class ExternalCommand : Command {
    val EXECUTION_SCRIPT = File(System.getProperty("user.home"), ".run.tmp.sh")

    val definition: String

    constructor(definition: String) {
        this.definition = definition
    }

    override fun run(arguments : List<String>) {
        // prep
        EXECUTION_SCRIPT.createNewFile()
        EXECUTION_SCRIPT.printWriter().use { out ->
            out.write("#!/bin/bash\n")
            out.write(insertArguments(definition, arguments))
        }

        // execute
        val process = Runtime.getRuntime().exec("bash " + EXECUTION_SCRIPT)
        // stdout
        val stdOut = BufferedReader(InputStreamReader(process.inputStream))
        stdOut.lines().forEach { line ->
            println(line)
        }
        // stderr
        val stdErr = BufferedReader(InputStreamReader(process.errorStream))
        stdErr.lines().forEach { line ->
            println(line)
        }
        process.waitFor()
        process.destroy()

        // cleanup
        EXECUTION_SCRIPT.delete()
    }

    private fun insertArguments(definition: String, arguments : List<String>): String {
        var parameterReplacedDefinition = definition
        arguments.forEachIndexed { i, argument ->
            parameterReplacedDefinition = parameterReplacedDefinition.replace("@{${i + 1}}", argument)
        }
        return parameterReplacedDefinition
    }

}