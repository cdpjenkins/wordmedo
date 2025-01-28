import com.cdpjenkins.wordlecheater.WordleCheater
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class WordleCheaterTest {
    private fun allLetters() = ('a'..'z')

    val cheater = WordleCheater()

    @Test
    fun `initially allows all letters`() {
        (0..4).forEach { position ->
            allLetters().forEach { letter ->
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
