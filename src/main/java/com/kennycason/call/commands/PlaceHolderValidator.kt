package com.kennycason.call.commands

/**
 * Created by kenny on 5/21/16.
 */
class PlaceHolderValidator {
    val placeHolderRegex = "\\@\\{\\d+\\}".toRegex()

    fun validate(definition: String, arguments: List<String>): ValidationResult {
        val matches = placeHolderRegex.findAll(definition)
        val placeHolders = mutableSetOf<Int>()

        matches.forEach { match ->
            val placeHolder = extractInteger(match.value)
            placeHolders.add(placeHolder)
            if (placeHolder > arguments.size) {
                return ValidationResult(false, "Placeholder @{$placeHolder} is larger than number of arguments passed in")
            }
        }
        if (arguments.size != placeHolders.size) {
            return ValidationResult(false, "Command is expecting ${placeHolders.size} arguments, however ${arguments.size} arguments were provided")
        }
        return return ValidationResult(true, "");
    }

    private fun extractInteger(placeHolder: String) : Int {
        return Integer.parseInt(
                placeHolder.replace(regex = "\\@\\{|\\}".toRegex(), replacement = ""))
    }
}

data class ValidationResult(val valid: Boolean, val message: String)