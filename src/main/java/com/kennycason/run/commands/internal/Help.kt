package com.kennycason.run.commands.internal

import com.kennycason.run.commands.internal.InternalCommand

/**
 * Created by kenny on 5/18/16.
 */
class Help : InternalCommand {
    override fun run(command : List<String>) {
        println(
                "<command1> [<command2> ...]\n\t\t" +
                        "Run a list of stored commands\n" +
                "list\n\t\t" +
                        "List all stored command with their definitions\n" +
                "add <command> <definition>\n\t\t" +
                        "Add a new command with it's definition\n" +
                "add <file/url>\n\t\t" +
                        "Add new commands from an existing library file\n" +
                "remove <command>\n\t\t" +
                        "Remove a command if exists\n" +
                "help" +
                        "\n\t\tDisplay current help method\n"
        )
    }
}