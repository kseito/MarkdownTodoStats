import java.io.File

fun main(args: Array<String>) {
    //https://qiita.com/niwasawa/items/533385a7c718a1dc39a3
    val fileName: String = args.joinToString()
    val file = File(fileName)
    for (i in SpentTimeCalculator().calculate(file)) {
        println("${i.title}: ${i.count}")
    }

}