package com.example.vma_project_2022_trade_game.data

import com.example.vma_project_2022_trade_game.GridItemModel
import com.example.vma_project_2022_trade_game.MyManager

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

    fun tradeDataModelFromString(str: String): TradeDataModel {
        val list = str.split(',')
        return if (list.size != 6) TradeDataModel(0, -1, -1, 0, -1, -1)
        else TradeDataModel(
            Integer.parseInt(list[0]),
            Integer.parseInt(list[1]),
            Integer.parseInt(list[2]),
            Integer.parseInt(list[3]),
            Integer.parseInt(list[4]),
            Integer.parseInt(list[5])
        )
    }


    fun gerResourcesForTextField(resNames: Map<String, String>): String {
        var str: String = ""
        for (i in 0 until resNames.size) {
            str += resNames[i.toString()]
            if (i < resNames.size - 1) str += ","
        }
        return str
    }

    fun createResActText(resValues: Map<Int, Int>): String {
        var formattedText: String = ""
        for (i in 0 until resValues.size) {
            formattedText += getStringWithSpacing(MyManager.gameActual.resNames[i.toString()]!!) + ":  ${resValues[i]}"
            if (i < resValues.size - 1) {
                formattedText += "\n"
            }

        }
        return formattedText
    }

    fun getStringWithSpacing(str: String, spacing: Int = 12): String {
        return str + " ".repeat(spacing - str.length)
    }

    fun generateResValues(resNames: Map<String, String> = MyManager.gameActual.resNames): Map<Int, Int> {
        var map: MutableMap<Int, Int> = mutableMapOf()
        for (i in 0 until resNames.size) {
            map[i] = 0
        }
        return map
    }

    fun getResValuesAsString(resAndValues: MutableMap<Int, Int>): String {
        var resValuesString = ""
        for (i in 0 until resAndValues.size) {
            resValuesString += resAndValues[i].toString()
            if (i < resAndValues.size - 1) {
                resValuesString += ","
            }

        }
        return resValuesString
    }

    fun getResValuesFromString(resValsString: String): Map<Int, Int> {
        val resValues: MutableMap<Int, Int> = mutableMapOf()
        val list = resValsString.split(',')
        for (i in list.indices) {
            resValues[i] = Integer.parseInt(list[i])
        }
        return resValues
    }

    fun rowResIdFromPos(pos: Int): Int = (pos / (MyManager.resCount + 1)) - 1

    fun colResIdFromPos(pos: Int): Int = (pos % (MyManager.resCount + 1)) - 1
    fun makeTrade(sellAmount: Int, res1RatioVal: Int, res2RatioVal: Int): Any {
        return (sellAmount * res2RatioVal) / res1RatioVal
    }


    /* fun gridItemText(): String = "$res1Val : $res2Val"

    fun row(): Int = intPos / (MyManager.resCount + 1)
    fun col(): Int = intPos % (MyManager.resCount + 1)

    fun getItemOpposite(): GridItemModel {
        return GridItemModel(Constants.fromTupleToPosInt(col(), row()), res2Val, res1Val)
    }

    override fun toString(): String {
        return "$intPos,$res1Val,$res2Val"
    }*/


}