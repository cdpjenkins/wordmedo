package com.cdpjenkins.wordmedocheater

fun main(args: Array<String>) {
    println("WordMeDo!")
    println()

    val wordMeDo = WordMeDo("rivet")

    val seenLetters = ('a'..'z').associateWith { 'b' }.toMutableMap()

    for (guess in args) {
        val result = wordMeDo.guess(guess)

        (guess zip result).forEach { (c, r) ->
            when (r) {
                'g' -> {
                    seenLetters[c] = 'g'
                    print("${BACKGROUND_GREEN}${c}$END")
                }
                'y' -> {
                    if (seenLetters[c] == 'b') seenLetters[c] = 'y'
                    print("${BACKGROUND_YELLOW}${c}$END")
                }
                else -> {
                    print("$c")
                }
            }
        }
        println()

        println(result)

        val stons =
            seenLetters
                .map { (c, u) ->
                    when (u) {
                        'b' -> "$c"
                        'y' -> "${BACKGROUND_YELLOW}${FOREGROUND_WHITE}$c$END"
                        'g' -> "${BACKGROUND_GREEN}${FOREGROUND_WHITE}$c$END"
                        else -> throw IllegalArgumentException("$u")
                    }
                }.joinToString(" ")
        println(stons)

        if (result == "ggggg") {
            println("Nice, you found the word: ${FOREGROUND_GREEN}${wordMeDo.word}$END")
        }
    }
}

class WordMeDo(
    val word: String,
) {
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
                if (guessChar == wordChar) {
                    'g'.also { charsInWordMutable[guessChar] = charsInWordMutable.getValue(guessChar) - 1 }
                } else if (charsInWordMutable.getValue(guessChar) > 0) {
                    'y'.also { charsInWordMutable[guessChar] = charsInWordMutable.getValue(guessChar) - 1 }
                } else {
                    'b'
                }
            }.joinToString("")
    }
}

val FOREGROUND_RED = "\u001b[31m"
val FOREGROUND_GREEN = "\u001b[32m"
val FOREGROUND_YELLOW = "\u001b[33m"
val FOREGROUND_WHITE = "\u001b[37m"

val BACKGROUND_RED = "\u001b[41m"
val BACKGROUND_GREEN = "\u001b[42m"
val BACKGROUND_YELLOW = "\u001b[43m"

val END = "\u001b[0m"
