package com.cdpjenkins.wordlecheater

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class WordMeDoTest {
    @Test
    fun `an exact match results in g`() {
        WordMeDo("toast").guess("tzzzz") shouldBe "gbbbb"
    }

    @Test
    fun `if a letter appears elsewhere in the word, we get y`() {
        WordMeDo("toast").guess("ztzzz") shouldBe "bybbb"
    }

    @Test
    fun `if the character appears elsewhere in the word then we can get two yellows`() {
        WordMeDo("toast").guess("batty") shouldBe "byyyb"
    }

    @Test
    fun `we only get as many yellows for a single char as there are instances of that char in the word`() {
        WordMeDo("toast").guess("choon") shouldBe "bbybb"
    }

    @Test
    fun `consuming a green letter appears to mean we get one less yellow as well`() {
        WordMeDo("toast").guess("tatty") shouldBe "gyybb"
        WordMeDo("toast").guess("poopy") shouldBe "bgbbb"
    }

    @ParameterizedTest
    @CsvSource(
        "heath,hight,gbbyy",
        "heath,breem,bbybb",

        "rivet,arise,byyby",
        "rivet,mount,bbbbg",
        "rivet,refit,gybyg",
        "rivet,rivet,ggggg",
        "rivet,tatty,ybbbb",
    )
    fun `lots of examples to hunt for bugs and corner cases`(word: String, guess: String, result: String) {
        WordMeDo(word).guess(guess) shouldBe result
    }
}
