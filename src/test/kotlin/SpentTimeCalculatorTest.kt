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
    fun calculateTest() {
        val file = File("src/test/resources/test_todo.md")
        val actual = SpentTimeCalculator().calculate(file, 30)
        actual shouldBeEqualTo mapOf("test0" to 90, "test1" to 30, "test2" to 0, "test3" to 60, "test4" to 0)
    }

    @Test
    fun createStructuredLinesTest() {
        val file = File("src/test/resources/test_todo.md")
        val actual = SpentTimeCalculator().createStructuredLines(file)
        actual.values.size shouldBeEqualTo 5
        actual.values[0].line.itemString shouldBeEqualTo "[ ] test0 ---"
        actual.values[1].apply {
            line.itemString shouldBeEqualTo "test1 -"
            child.size shouldBeEqualTo 3
            child[0].line.itemString shouldBeEqualTo "[ ] test11 -"
            child[1].line.itemString shouldBeEqualTo "[ ] test12"
            child[2].apply {
                line.itemString shouldBeEqualTo "[ ] test13"
                child.size shouldBeEqualTo 2
                child[0].line.itemString shouldBeEqualTo "[ ] test13-1 --"
                child[1].line.itemString shouldBeEqualTo "[ ] test13-2"
            }
        }
        actual.values[4].line.itemString shouldBeEqualTo "test4"
    }
}