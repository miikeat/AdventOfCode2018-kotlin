

fun main() {
    val freq: MutableSet<Int> = mutableSetOf<Int>(0)

    var i = 0
    var current = 0
    do {
        current = freq.last() +  data[i%(data.size-1)]
        i++
    } while(freq.add(current))
    println (current)
    println (i)
    println (i/(data.size-1))
}