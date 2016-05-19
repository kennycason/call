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

    override fun run(ignored : List<String>) {
        // prep
        EXECUTION_SCRIPT.createNewFile()
        EXECUTION_SCRIPT.printWriter().use { out ->
            out.write("#!/bin/bash\n")
            out.write(definition)
        }

        // execute
        val process = Runtime.getRuntime().exec("bash " + EXECUTION_SCRIPT)
        val reader = BufferedReader(InputStreamReader(process.getInputStream()))
        reader.lines().forEach { line ->
            println(line)
        }
        process.waitFor();
        process.destroy()

        // cleanup
        EXECUTION_SCRIPT.delete()
    }

}