import com.cdpjenkins.wordlecheater.ALL_LETTERS
import com.cdpjenkins.wordlecheater.WordleCheater
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class WordleCheaterTest {
    private fun allLetters() = ('a'..'z')

    val cheater = WordleCheater()

    @Test
    fun `matches a word that we already know`() {
        cheater.oneRound("abcde", "ggggg")

        cheater.matches("abcde") shouldBe true
    }

    @Test
    fun `matches a word we don't know`() {
        cheater.oneRound("abcde", "yyyyy")

        cheater.matches("bcdea") shouldBe true
        cheater.matches("abcde") shouldBe false
    }

    @Test
    fun `eliminates all other letters once it has seen five letters`() {
        cheater.oneRound("audio", "ybbyb")
        cheater.oneRound("fever", "bbbbg")
        cheater.oneRound("chalk", "gbybb")
        cheater.oneRound("muggy", "bbgbb")

        cheater.ruledOutLetters.toSet() shouldBe ALL_LETTERS.minus("acgir".toSet())
    }

    @Test
    fun `initially allows all letters`() {
        (0..4).forEach { position ->
            ALL_LETTERS.forEach { letter ->
                cheater.allowsLetterAtPosition(position, letter) shouldBe true
            }
        }
    }

    @Test
    fun `yellow means rule out letter at position`() {
        cheater.yellowLetter(0, 'a')

        cheater.allowsLetterAtPosition(0, 'a') shouldBe false
        allLettersExcept('a').forEach { cheater.allowsLetterAtPosition(0, it) shouldBe true }
    }

    private fun allLettersExcept(c: Char) = allLetters().minus(c)

    @Test
    fun `green means rule out all other letters at position`() {
        cheater.greenLetter(0, 'a')

        cheater.allowsLetterAtPosition(0, 'a') shouldBe true
        allLettersExcept('a').forEach { cheater.allowsLetterAtPosition(0, it) shouldBe false }
    }

    @Test
    fun `black means rule out letter at all positions`() {
        cheater.blackLetter(0, 'a')

        cheater.allowsLetterAtPosition(0, 'a') shouldBe false
        (0..4).forEach { position ->
            cheater.allowsLetterAtPosition(position, 'a') shouldBe false
            ('b'..'z').forEach { cheater.allowsLetterAtPosition(position, it) shouldBe true }
        }
    }

    @Test
    fun `black doesn't rule out a letter everywhere if that letter has already been seen and known to be present`() {
        cheater.yellowLetter(0, 'a')
        cheater.blackLetter(1, 'a')

        cheater.allowsLetterAtPosition(0, 'a') shouldBe false
        cheater.allowsLetterAtPosition(1, 'a') shouldBe false
        cheater.allowsLetterAtPosition(2, 'a') shouldBe true
        cheater.allowsLetterAtPosition(3, 'a') shouldBe true
        cheater.allowsLetterAtPosition(4, 'a') shouldBe true
    }
}
