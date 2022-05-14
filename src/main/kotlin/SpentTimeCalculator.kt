import model.OneLine
import java.io.File

class SpentTimeCalculator {

    fun calculate(file: File, unitInMinutes: Int): Map<String, Int> {
        val list = mutableMapOf<String, Int>()
        val lines = file.readLines()
            .map { OneLine(it) }
        lines.map { line ->
            if (line.hasItem()) {
                val item = line.extractItem()
                list.put(item.title, item.count * unitInMinutes)
            } else {
                //Do nothing
            }
        }
        return list
    }
}