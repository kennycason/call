package com.kennycason.run.commands.internal

import com.kennycason.run.CommandLibrary
import com.kennycason.run.commands.PlaceHolderValidator
import java.io.*
import java.lang.instrument.ClassDefinition
import java.util.regex.Pattern

/**
 * Created by kenny on 5/18/16.
 *
 * Due to many random issues with running arbitrary raw commands in java, this command creates a temporary
 * shell script, copies all commands to it, and runs the script.
 */
class StoredCommand : Command {
    val executionScript = File(System.getProperty("user.home"), ".run.tmp.sh")
    val placeHolderValidator = PlaceHolderValidator()
    val definition: String

    constructor(definition: String) {
        this.definition = definition
    }

    override fun run(arguments : List<String>) {
        val validationResult = placeHolderValidator.validate(definition, arguments)
        if (!validationResult.valid) {
            throw RuntimeException(validationResult.message)
        }
        // prep
        executionScript.createNewFile()
        executionScript.printWriter().use { out ->
            out.write("#!/bin/bash\n")
            out.write(insertArguments(arguments))
        }

        // execute
        val process = Runtime.getRuntime().exec("bash " + executionScript)
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
        executionScript.delete()
    }

    private fun insertArguments(arguments : List<String>): String {
        var parameterReplacedDefinition = definition
        arguments.forEachIndexed { i, argument ->
            parameterReplacedDefinition = parameterReplacedDefinition.replace("@{${i + 1}}", argument)
        }
        return parameterReplacedDefinition
    }

}