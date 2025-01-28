package org.example

import com.cdpjenkins.wordlecheater.WordleCheater
import java.io.File

fun main(args: Array<String>) {
    val cheater = WordleCheater()

    args.toList().windowed(size = 2, step = 2).forEach { (attempt, result) ->
        println("$attempt $result")

        cheater.guess(attempt, result)
    }

    val dictionaryWords = File("/usr/share/dict/words").readLines().filter { it.length == 5 }

    val possibleWords = dictionaryWords.filter { cheater.matches(it) }

    println(possibleWords)
}
