fun identicalCharacters(id1: String,id2:String): String {
    return id1.zip(id2).map { if (it.first == it.second)  it.first.toString() else "" }.fold ("") { acc, e -> acc + e}
}

fun main() {
    val similar = mutableListOf<String>()
    data2.forEachIndexed {i, id ->
        similar.addAll(data2.drop(i).map { identicalCharacters(id, it) })
    }

    println(similar.filter { it.length==data2[0].length-1 })
}