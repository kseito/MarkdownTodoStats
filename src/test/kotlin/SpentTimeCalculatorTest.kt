import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.io.File

/**
 * やること
 * - 最大３階層まで抽出処理を行う
 * - 結果の出力
 */

class SpentTimeCalculatorTest {

    @Test
    fun test() {
        val file = File("src/test/resources/test_todo.md")
        println(file.readText())
        val actual = SpentTimeCalculator().calculate(file, 30)
        actual shouldBeEqualTo mapOf("test0" to 90, "test1" to 30, "test2" to 0, "test3" to 60, "test4" to 0)
    }
}