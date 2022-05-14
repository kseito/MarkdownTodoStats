import java.io.File

class SpentTimeCalculator {

    fun calculate(file: File, unitInMinutes: Int): Map<String, Int> {
        val list = mutableMapOf<String, Int>()
        val lines = file.readLines()
        lines.map { line ->
            if (line.startsWith("- ")) {
                val item = line.substring(2 until line.length)
                if (item.startsWith("[ ] ") || item.startsWith("[x] ")) {
                    val moga = item.substring(4 until item.length)
                    val title = moga.split(" ")[0]
                    val time = moga.split(" ")[1].count { it == '-' } * unitInMinutes
                    list.put(title, time)
                } else {
                    val hoge = item.split(" ")
                    list.put(hoge[0], hoge[1].count { it == '-' } * unitInMinutes)
                }
            } else {
                //Do nothing
            }
        }
        return list
    }
}