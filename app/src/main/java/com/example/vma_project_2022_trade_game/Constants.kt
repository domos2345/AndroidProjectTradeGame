package com.example.vma_project_2022_trade_game

object Constants {

    fun fromPosToTupleString(pos: Int, resCount: Int): String {
        return "" + pos / (resCount + 1) + ":" + pos % (resCount + 1)

    }

    fun fromTupleToPosInt(/*row*/ row: Int, /*column*/
                                  column: Int,
                                  resCount: Int = MyManager.gameActual.resCount
    ): Int {
        return row * (resCount + 1) + column
    }

    fun getRes1NameFromPos(
        pos: Int,
        resNames: Map<String, String> = MyManager.gameActual.resNames,
        resCount: Int = MyManager.gameActual.resCount
    ): String? {
        println(MyManager.gameActual.resNames.entries.toString())
        return if (resNames.containsKey((pos / (resCount + 1)).toString())) {
            resNames[(pos / (resCount + 1) - 1).toString()]
        } else
            "res???"

    }

    fun getRes2NameFromPos(
        pos: Int,
        resNames: Map<String, String> = MyManager.gameActual.resNames,
        resCount: Int = MyManager.gameActual.resCount
    ): String? {
        return if (resNames.containsKey((pos / (resCount + 1)).toString())) {
            resNames[(pos % (resCount + 1) - 1).toString()]
        } else
            "res???"

    }

    fun isClickableGridItem(pos: Int, resCount: Int = MyManager.gameActual.resCount): Boolean {
        return !(pos / (resCount + 1) == 0 || pos % (resCount + 1) == 0 || (pos / (resCount + 1) == pos % (resCount + 1)))
    }

    fun gridItemFromString(str: String): GridItemModel {
        val list = str.split(',')
        return if (list.size != 3) GridItemModel(0, -1, -1)
        else GridItemModel(
            Integer.parseInt(list[0]),
            Integer.parseInt(list[1]),
            Integer.parseInt(list[2])
        )
    }


}