package com.cdpjenkins.wordlecheater

import java.io.File

fun main(args: Array<String>) {
    val cheater = WordleCheater()

    val dictionaryWords = File("/usr/share/dict/words").readLines().filter { it.length == 5 }

    args.toList().windowed(size = 2, step = 2).forEach { (attempt, result) ->
        cheater.guess(attempt, result)

        val possibleWords = dictionaryWords.filter { cheater.matches(it) }
        println("$attempt $result ... ${possibleWords.size} possible solutions")

        println(possibleWords.size)
    }

    val possibleWords = dictionaryWords.filter { cheater.matches(it) }
    println(possibleWords)

    if (possibleWords.size > 1) {
        println("Remaining possible letters ${cheater.remainingPossibleLetters()}")
    }
}
