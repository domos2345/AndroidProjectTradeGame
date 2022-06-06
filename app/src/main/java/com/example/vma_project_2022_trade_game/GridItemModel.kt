package com.example.vma_project_2022_trade_game


data class GridItemModel(val intPos: Int, val res1Val: Int, val res2Val: Int)

/*fun gridItemText(): String = "$res1Val : $res2Val"

fun row(): Int = intPos / (MyManager.resCount + 1)
fun col(): Int = intPos % (MyManager.resCount + 1)

fun getItemOpposite(): GridItemModel {
    return GridItemModel(Constants.fromTupleToPosInt(col(), row()), res2Val, res1Val)
}

override fun toString(): String {
    return "$intPos,$res1Val,$res2Val"
}*/

fun GridItemModel.gridItemText(): String = "$res1Val : $res2Val"

fun GridItemModel.row(): Int = intPos / (MyManager.resCount + 1)
fun GridItemModel.col(): Int = intPos % (MyManager.resCount + 1)


fun GridItemModel.getItemOpposite(): GridItemModel {
    return GridItemModel(Constants.fromTupleToPosInt(col(), row()), res2Val, res1Val)
}