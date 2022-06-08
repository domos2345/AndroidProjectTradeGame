package com.example.vma_project_2022_trade_game

import com.example.vma_project_2022_trade_game.MyManager
import com.example.vma_project_2022_trade_game.data.Constants


class GridItemModel() {
    var intPos: Int = 0
    var res1Val: Int = 0
    var res2Val: Int = 0

    constructor(intPos: Int, res1Val: Int, res2Val: Int) : this() {
        this.intPos = intPos
        this.res1Val = res1Val
        this.res2Val = res2Val
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GridItemModel

        if (intPos != other.intPos) return false
        if (res1Val != other.res1Val) return false
        if (res2Val != other.res2Val) return false

        return true
    }

    override fun hashCode(): Int {
        var result = intPos
        result = 31 * result + res1Val
        result = 31 * result + res2Val
        return result
    }

    override fun toString(): String {
        return "GridItemModel(intPos=$intPos, res1Val=$res1Val, res2Val=$res2Val)"
    }

}

/*fun gridItemText(): String = "$res1Val : $res2Val"

fun row(): Int = intPos / (MyManager.resCount + 1)
fun col(): Int = intPos % (MyManager.resCount + 1)

fun getItemOpposite(): GridItemModel {
    return GridItemModel(Constants.fromTupleToPosInt(col(), row()), res2Val, res1Val)
}
}*/
fun GridItemModel.toBundleString(): String {
    return "$intPos,$res1Val,$res2Val"
}

fun GridItemModel.gridItemText(): String = "$res1Val : $res2Val"

fun GridItemModel.row(): Int = intPos / (MyManager.resCount + 1)
fun GridItemModel.col(): Int = intPos % (MyManager.resCount + 1)


fun GridItemModel.getItemOpposite(): GridItemModel {
    return GridItemModel(Constants.fromTupleToPosInt(col(), row()), res2Val, res1Val)
}
