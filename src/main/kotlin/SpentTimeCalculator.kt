import model.*
import java.io.File

class SpentTimeCalculator {

    fun calculate(file: File): List<SummarizedItem> {
        val list = mutableListOf<SummarizedItem>()
        val collection = createStructuredLines(file)
        collection.values.map { line ->
            val item = Item.create(line)
            val summarizedItem = SummarizedItem(item.title, sumChildrenSpentTime(item))
            list.find { it.title == summarizedItem.title }
                ?.let {
                    val index = list.indexOf(it)
                    val existingItem = list[index]
                    list.set(index, existingItem + summarizedItem)
                } ?: list.add(summarizedItem)
        }
        return list.sortedByDescending { it.count }
    }

    private fun sumChildrenSpentTime(item: Item): Int {
        val sum = item.children
            ?.sumOf { sumChildrenSpentTime(it) } ?: 0
        return sum + item.count
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