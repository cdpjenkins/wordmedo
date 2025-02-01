package com.cdpjenkins.wordlecheater

fun main(args: Array<String>) {
    println("WordMeDo!")
    println()

    val wordMeDo = WordMeDo("rivet")

    val letters =
        ('a'..'z').associateWith { 'b' }.toMutableMap()

    letters['g'] = 'g'
    letters['y'] = 'y'

//    while (true) {
    val RED = "\u001b[31m"
    val GREEN = "\u001b[32m"
    val YELLOW = "\u001b[33m"
    val END = "\u001b[0m"

    println("Hello ${RED} World!${END}")
    letters.forEach { (c, u) ->
        when (u) {
            'b' -> print("$c")
            'y' -> print("${YELLOW}$c${END}")
            'g' -> print("${GREEN}$c${END}")
        }
    }

//    }
}

class WordMeDo(val word: String) {
    fun guess(guess: String): String {

        val charsInWord = word.toList()

        val charsInWordMutable =
            charsInWord
                .toMutableList()
                .groupingBy { it }
                .eachCount()
                .toMutableMap()
                .withDefault { 0 }

        return (guess zip word)
            .map { (guessChar, wordChar) ->
                if (guessChar == wordChar) 'g'.also {
                    charsInWordMutable[guessChar] = charsInWordMutable.getValue(guessChar) - 1
                }
                else if (charsInWordMutable.getValue(guessChar) > 0) 'y'.also {
                    charsInWordMutable[guessChar] = charsInWordMutable.getValue(guessChar) - 1
                }
                else 'b'
            }.joinToString("")
    }
}
