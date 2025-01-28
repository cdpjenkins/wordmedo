package com.cdpjenkins.wordlecheater

class WordleCheater {
    val allowedLettersAtPositions: MutableMap<Int, Set<Char>> =
        (0..4).map { position ->
            val allowedLetters = ('a'..'z').toList().toSet()

            position to allowedLetters
        }.toMap().toMutableMap()

    val seenLetters: MutableSet<Char> = HashSet()
    val ruledOutLetters: MutableSet<Char> = HashSet()

    fun matches(word: String) =
        (0..4).all { pos ->
            allowsLetterAtPosition(pos, word[pos])
        }

    fun oneRound(guess: String, result: String) {
        (guess zip result).forEachIndexed { pos, (guessChar, resultChar) ->
            when (resultChar) {
                'b' -> blackLetter(pos, guessChar)
                'y' -> yellowLetter(pos, guessChar)
                'g' -> greenLetter(pos, guessChar)
            }
        }
    }

    fun allowsLetterAtPosition(position: Int, letter: Char): Boolean {
        return allowedLettersAtPositions[position]?.contains(letter) ?: false
    }

    fun yellowLetter(position: Int, c: Char) {
        ruleOutLetterAtPosition(position, c)

        addSeenLetter(c)
    }

    fun greenLetter(position: Int, c: Char) {
        letterKnownAtPosition(position, c)

        addSeenLetter(c)
    }

    private fun addSeenLetter(c: Char) {
        seenLetters.add(c)

        if (seenLetters.size == 5) {
            (ALL_LETTERS.minus(seenLetters)).forEach { letterToEliminate->
                eliminateLetterAtAllPositions(letterToEliminate)
            }
        }
    }

    fun blackLetter(position: Int, c: Char) {
        if (c in seenLetters) {
            ruleOutLetterAtPosition(position, c)
        } else {
            eliminateLetterAtAllPositions(c)
        }
    }

    private fun eliminateLetterAtAllPositions(c: Char) {
        ruledOutLetters.add(c)

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

val ALL_LETTERS = ('a'..'z').toList()
