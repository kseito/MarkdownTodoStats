package model

sealed class Item(open val title: String, open val count: Int) {

    companion object Factory {
        fun create(line: OneLine): Item {
            val itemString = line.itemString
            return if (itemString.startsWith(Checked.CHECKED)) {
                Checked.create(itemString, true)
            } else if (itemString.startsWith(Checked.UNCHECKED)) {
                Checked.create(itemString, false)
            } else {
                val (title, count) = extractTitleAndCount(itemString)
                Normal(title, count)
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

    class Normal(override val title: String, override val count: Int) : Item(title, count)
    class Checked(override val title: String, override val count: Int, val checked: Boolean) : Item(title, count) {

        companion object {
            const val CHECKED = "[x] "
            const val UNCHECKED = "[ ] "

            fun create(itemStringWithCheckBox: String, checked: Boolean): Checked {
                val itemString =
                    itemStringWithCheckBox.substring(Checked.CHECKED.length until itemStringWithCheckBox.length)
                val (title, count) = extractTitleAndCount(itemString)
                return Checked(title, count, checked)
            }
        }
    }
}
