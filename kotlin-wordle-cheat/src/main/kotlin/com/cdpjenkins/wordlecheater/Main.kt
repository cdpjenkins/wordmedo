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
//    println("possible words: $possibleWords")

    val freqs = ('a'..'z')
        .associateWith { letter ->
            val letterFrequency = possibleWords.count { it.contains(letter) }
//            println("$letter: $letterFrequency")
            letterFrequency
        }

    val someWords = possibleWords.map { w ->
        w to w.toSet().map { freqs[it]!! }.sum()
    }.sortedBy { it.second }
        .reversed()
        .take(15)
        .reversed()

    someWords.forEach { println(it) }

//    if (possibleWords.size > 1) {
//        if (cheater.seenLetters.size < 5) {
//            println("Remaining possible letters ${cheater.remainingPossibleLetters()}")
//
//            val wordsToFindMoreLetters =
//                dictionaryWords.sortedBy { (it.toSet() intersect cheater.remainingPossibleLetters().toSet()).size }
//                    .reversed()
//                    .take(200)
//            println("try these to find more letters: ${wordsToFindMoreLetters}")
//        }
//    }
}
