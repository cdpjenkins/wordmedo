package com.cdpjenkins.wordlecheater

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class WordMeDoPossibilitiesTest {
    private val cheater = WordMeDoPossibilities()

    @Test
    fun `matches a word that we already know`() {
        cheater.guess("abcde", "ggggg")

        cheater.matches("abcde") shouldBe true
    }

    @Test
    fun `matches a word we don't know`() {
        cheater.guess("abcde", "yyyyy")

        cheater.matches("bcdea") shouldBe true
        cheater.matches("abcde") shouldBe false
    }

    @Test
    fun `eliminates all other letters once it has seen five letters`() {
        cheater.guess("audio", "ybbyb")
        cheater.guess("fever", "bbbbg")
        cheater.guess("chalk", "gbybb")
        cheater.guess("muggy", "bbgbb")

        cheater.ruledOutLetters.toSet() shouldBe allLetters.minus("acgir".toSet())
    }

    @Test
    fun `only matches a word if it contains all the required yellow letters`() {
        cheater.guess("xxxxe", "bbbby")

        cheater.matches("fever") shouldBe true
        cheater.matches("quack") shouldBe false // doesn't contain an e
    }

    @Test
    fun `initially allows all letters`() {
        allPositions.forEach { position ->
            allLetters.forEach { letter ->
                cheater.allowsLetterAtPosition(position, letter) shouldBe true
            }
        }
    }

    @Test
    fun `yellow means rule out letter at position`() {
        cheater.yellowResult(0, 'a')

        cheater.allowsLetterAtPosition(0, 'a') shouldBe false
        allLettersExcept('a').forEach { cheater.allowsLetterAtPosition(0, it) shouldBe true }
    }

    @Test
    fun `green means rule out all other letters at position`() {
        cheater.greenResult(0, 'a')

        cheater.allowsLetterAtPosition(0, 'a') shouldBe true
        allLettersExcept('a').forEach { cheater.allowsLetterAtPosition(0, it) shouldBe false }
    }

    @Test
    fun `black means rule out letter at all positions`() {
        cheater.blackResult(0, 'a')

        cheater.allowsLetterAtPosition(0, 'a') shouldBe false
        allPositions.forEach { position ->
            cheater.allowsLetterAtPosition(position, 'a') shouldBe false
            allLettersExcept('a').forEach { cheater.allowsLetterAtPosition(position, it) shouldBe true }
        }
    }

    @Test
    fun `black doesn't rule out a letter everywhere if that letter has already been seen and known to be present`() {
        cheater.yellowResult(0, 'a')
        cheater.blackResult(1, 'a')

        cheater.allowsLetterAtPosition(0, 'a') shouldBe false
        cheater.allowsLetterAtPosition(1, 'a') shouldBe false
        cheater.allowsLetterAtPosition(2, 'a') shouldBe true
        cheater.allowsLetterAtPosition(3, 'a') shouldBe true
        cheater.allowsLetterAtPosition(4, 'a') shouldBe true
    }

    private fun allLettersExcept(
        @Suppress("SameParameterValue") c: Char,
    ) = allLetters.minus(c)
}
