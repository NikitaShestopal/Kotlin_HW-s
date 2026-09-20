package Hw2

//На вхід подається дійсне число. Вивести його з n знаками після коми.
import java.util.Locale



fun main() {
    val number = 3.1415926535
    val n = 3

    val formatted = "%.${n}f".format(number)
    println(formatted)
}