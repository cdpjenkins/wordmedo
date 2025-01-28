package com.cdpjenkins.wordlecheater

class WordleCheater {
    private val possibileLettersAt: MutableMap<Int, Set<Char>> =
        allPositions.associateWith { allLetters.toSet() }.toMutableMap()

    val seenLetters: MutableSet<Char> = HashSet()
    val ruledOutLetters: MutableSet<Char> = HashSet()

    fun matches(word: String) = allPositions.all { pos -> allowsLetterAtPosition(pos, word[pos]) }

    fun guess(guess: String, result: String) {
        (guess zip result).forEachIndexed { pos, (guessChar, resultChar) ->
            when (resultChar) {
                'b' -> blackResult(pos, guessChar)
                'y' -> yellowResult(pos, guessChar)
                'g' -> greenResult(pos, guessChar)
            }
        }
    }

    fun allowsLetterAtPosition(position: Int, letter: Char) =
        possibileLettersAt[position]?.contains(letter) ?: false

    fun yellowResult(position: Int, c: Char) {
        eliminatePossibilityAtPosition(position, c)
        addSeenLetter(c)
    }

    fun greenResult(position: Int, c: Char) {
        letterKnownAtPosition(position, c)
        addSeenLetter(c)
    }

    fun blackResult(position: Int, c: Char) {
        if (c in seenLetters) {
            eliminatePossibilityAtPosition(position, c)
        } else {
            eliminatPoissibilityAtAllPositions(c)
        }
    }

    private fun addSeenLetter(c: Char) {
        seenLetters.add(c)

        if (seenLetters.size == 5) {
            (allLetters.minus(seenLetters)).forEach { letterToEliminate->
                eliminatPoissibilityAtAllPositions(letterToEliminate)
            }
        }
    }

    private fun eliminatPoissibilityAtAllPositions(c: Char) {
        ruledOutLetters.add(c)
        allPositions.forEach { p -> eliminatePossibilityAtPosition(p, c) }
    }

    private fun letterKnownAtPosition(position: Int, c: Char) {
        possibileLettersAt[position] = setOf(c)
    }

    private fun eliminatePossibilityAtPosition(position: Int, c: Char) {
        possibileLettersAt[position] = possibileLettersAt[position]!!.minus(c)
    }
}

val allPositions = 0..4
val allLetters = ('a'..'z').toList()
