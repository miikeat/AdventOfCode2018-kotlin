

typealias fabrictype2 = Array<Array<MutableList<Piece>>>

fun applyPiece2(fabric:fabrictype2, piece: Piece) {
    for (x in piece.down..piece.down+piece.height-1) {
        for (y in piece.right..piece.right+piece.width-1) {
            fabric[x][y].add(piece)
        }

    }
}


fun main() {
    val fabric :fabrictype2 = Array(1000, { Array(1000, { mutableListOf<Piece>() })})
    val piecemap : MutableMap<Int,Int> = mutableMapOf()
    println("Max height: " + data3.map{it.down + it.height}.max())
    println("Max width: " + data3.map{it.right + it.width}.max())
    data3.forEach {applyPiece2(fabric, it)}
    fabric.flatten().forEach { square -> square.forEach { piecemap[it.id] = maxOf(piecemap.getOrDefault(it.id,0),square.size)  } }
    println("Overlapping squareinches: " + piecemap.filterValues { it == 1 })
}