import model.OneLine
import model.StructuredOneLine
import model.StructuredOneLineCollection
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

    fun createStructuredLines(file: File): StructuredOneLineCollection {
        val list = mutableListOf<StructuredOneLine>()
        val indentSize = 2

        var currentParent: StructuredOneLine? = null
        var currentChild: StructuredOneLine? = null
        var currentGrandchild: StructuredOneLine? = null
        file.readLines().forEach {
            if (it.startsWith("- ")) {
                currentParent = StructuredOneLine(OneLine(it), mutableListOf())
                list.add(currentParent!!)
            } else if (it.startsWith(" ".repeat(indentSize) + "- ")) {
                val itemString = it.substring(indentSize, it.length)
                currentChild = StructuredOneLine(OneLine(itemString), mutableListOf())
                currentParent?.child?.add(currentChild!!)
            } else if (it.startsWith(" ".repeat(indentSize * 2) + "- ")) {
                val itemString = it.substring(indentSize * 2, it.length)
                currentGrandchild = StructuredOneLine(OneLine(itemString), mutableListOf())
                currentChild?.child?.add(currentGrandchild!!)
            }
        }
        return StructuredOneLineCollection(list.toList())
    }
}