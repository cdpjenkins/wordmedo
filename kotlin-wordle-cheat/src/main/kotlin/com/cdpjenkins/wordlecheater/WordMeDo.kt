package com.cdpjenkins.wordlecheater

class WordMeDo(val word: String) {
    fun guess(guess: String) =
        (guess zip word)
            .map { (guessChar, wordChar) ->
                if (guessChar == wordChar) 'g'
                else if (guessChar in word) 'y'
                else 'b'
            }.joinToString("")
}
