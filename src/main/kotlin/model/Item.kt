package model

sealed class Item(open val title: String, open val count: Int, open val children: List<Item>?) {

    companion object Factory {
        fun create(line: StructuredOneLine): Item {
            val itemString = line.line.itemString
            return if (itemString.startsWith(Checked.CHECKED)) {
                Checked.create(line, true)
            } else if (itemString.startsWith(Checked.UNCHECKED)) {
                Checked.create(line, false)
            } else {
                Normal.create(line)
            }
        }

        private fun extractTitleAndCount(itemString: String): Pair<String, Int> {
            val titleAndCount = itemString.split(" ")
            val title = titleAndCount[0]
            val count = if (titleAndCount.size == 2) {
                titleAndCount[1].count { it == '-' }
            } else {
                0
            }
            return Pair(title, count)
        }
    }

    class Normal(
        override val title: String,
        override val count: Int,
        override val children: List<Item>?
    ) : Item(title, count, children) {

        companion object {
            fun create(line: StructuredOneLine): Normal {
                val children = mutableListOf<Item>()
                line.child.forEach {
                    val item = Factory.create(it)
                    children.add(item)
                }
                val (title, count) = extractTitleAndCount(line.line.itemString)
                return Normal(title, count, children)
            }
        }
    }

    class Checked(
        override val title: String,
        override val count: Int,
        override val children: List<Item>?,
        val checked: Boolean
    ) : Item(title, count, children) {

        companion object {
            const val CHECKED = "[x] "
            const val UNCHECKED = "[ ] "

            fun create(line: StructuredOneLine, checked: Boolean): Checked {
                val children = mutableListOf<Item>()
                line.child.forEach {
                    val item = Factory.create(it)
                    children.add(item)
                }
                val itemString =
                    line.line.itemString.substring(CHECKED.length until line.line.itemString.length)
                val (title, count) = extractTitleAndCount(itemString)
                return Checked(title, count, children, checked)
            }
        }
    }
}
