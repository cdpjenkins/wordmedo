package com.cdpjenkins.wordlecheater

class WordleCheater {
    private val possibleLettersAt: MutableMap<Int, Set<Char>> =
        allPositions.associateWith { allLetters.toSet() }.toMutableMap()

    val seenLetters: MutableSet<Char> = HashSet()
    val ruledOutLetters: MutableSet<Char> = HashSet()

    fun matches(word: String) =
        allPositions.all { pos -> allowsLetterAtPosition(pos, word[pos]) } &&
            seenLetters.all { word.contains(it) }

    fun guess(
        guess: String,
        result: String,
    ) {
        (guess zip result).forEachIndexed { pos, (guessChar, resultChar) ->
            when (resultChar) {
                'b' -> blackResult(pos, guessChar)
                'y' -> yellowResult(pos, guessChar)
                'g' -> greenResult(pos, guessChar)
            }
        }
    }

    fun remainingPossibleLetters(): List<Char> = allLetters - ruledOutLetters - seenLetters

    fun allowsLetterAtPosition(
        position: Int,
        letter: Char,
    ) = possibleLettersAt[position]?.contains(letter) ?: false

    fun yellowResult(
        position: Int,
        c: Char,
    ) {
        eliminatePossibilityAtPosition(position, c)
        addSeenLetter(c)
    }

    fun greenResult(
        position: Int,
        c: Char,
    ) {
        letterKnownAtPosition(position, c)
        addSeenLetter(c)
    }

    fun blackResult(
        position: Int,
        c: Char,
    ) {
        if (c in seenLetters) {
            eliminatePossibilityAtPosition(position, c)
        } else {
            eliminatePossibilityAtAllPositions(c)
        }
    }

    private fun addSeenLetter(c: Char) {
        seenLetters.add(c)

        if (seenLetters.size == 5) {
            (allLetters.minus(seenLetters)).forEach { letterToEliminate ->
                eliminatePossibilityAtAllPositions(letterToEliminate)
            }
        }
    }

    private fun eliminatePossibilityAtAllPositions(c: Char) {
        ruledOutLetters.add(c)
        allPositions.forEach { p -> eliminatePossibilityAtPosition(p, c) }
    }

    private fun letterKnownAtPosition(
        position: Int,
        c: Char,
    ) {
        possibleLettersAt[position] = setOf(c)
    }

    private fun eliminatePossibilityAtPosition(
        position: Int,
        c: Char,
    ) {
        possibleLettersAt[position] = possibleLettersAt[position]!!.minus(c)
    }
}

val allPositions = 0..4
val allLetters = ('a'..'z').toList()
