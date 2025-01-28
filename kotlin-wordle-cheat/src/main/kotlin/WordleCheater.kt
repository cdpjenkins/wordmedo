package com.cdpjenkins.wordlecheater

class WordleCheater {
    val allowedLettersAtPositions: MutableMap<Int, Set<Char>> =
        (0..4).map { position ->
            val allowedLetters = ('a'..'z').toList().toSet()

            position to allowedLetters
        }.toMap().toMutableMap()

    fun allowsLetterAtPosition(position: Int, letter: Char): Boolean {
        return allowedLettersAtPositions[position]?.contains(letter) ?: false
    }

    fun yellowLetter(position: Int, c: Char) {
        ruleOutLetterAtPosition(position, c)
    }

    fun greenLetter(position: Int, c: Char) {
        letterKnownAtPosition(position, c)
    }

    fun blackLetter(position: Int, c: Char) {
        (0..4).forEach { p ->
            ruleOutLetterAtPosition(p, c)
        }
    }

    private fun letterKnownAtPosition(position: Int, c: Char) {
        allowedLettersAtPositions.put(position, setOf(c))
    }

    private fun ruleOutLetterAtPosition(position: Int, c: Char) {
        allowedLettersAtPositions.put(position, allowedLettersAtPositions[position]!!.minus(c))
    }
}
