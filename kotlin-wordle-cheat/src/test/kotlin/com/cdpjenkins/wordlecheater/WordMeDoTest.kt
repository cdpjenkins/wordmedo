package com.cdpjenkins.wordlecheater

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

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

//    @Test
//    fun `we only get as many yellows for a single char as there are instances of that char in the word`() {
//        WordMeDo("toast").guess("choon") shouldBe "bbybb"
//    }
}
