package com.cdpjenkins.wordlecheater

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
