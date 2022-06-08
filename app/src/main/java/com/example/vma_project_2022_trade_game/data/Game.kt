package com.example.vma_project_2022_trade_game.data

import com.example.vma_project_2022_trade_game.GridItemModel
import com.example.vma_project_2022_trade_game.MyManager
import com.example.vma_project_2022_trade_game.getItemOpposite

class Game(
    val nameOfGame: String,
    val resCount: Int,
    val resNames: Map<String, String>,
    val maxRatio: Int,
    val isOnlyOneToX: Boolean,
    val tables: MutableMap<String, MutableMap<String, GridItemModel>> = mutableMapOf()
) {

    fun generateGridItems(phase: Int) {
        var tableAct: MutableMap<String, GridItemModel> = mutableMapOf<String, GridItemModel>()
        //val tableAct: MutableMap<String, String> = mutableMapOf<String, String>()

        for (i in resCount..((resCount + 1) * (resCount + 1))) {
            if (Constants.isClickableGridItem(i) && !tableAct.containsKey(i.toString())) {
                val item: GridItemModel = generateOneItem(i)
                val itemOpposite = item.getItemOpposite()
                tableAct[item.intPos.toString()] = item
                tableAct[itemOpposite.intPos.toString()] = itemOpposite


            }
        }
        tables[phase.toString()] = tableAct
        //tables.add(tableAct)
    }

    private fun generateOneItem(pos: Int): GridItemModel {
        var areRatiosValid = false
        val range = (1..8)
        var randInt1 = range.random()
        var randInt2 = range.random()
        while (!areRatiosValid) {
            randInt1 = range.random()
            randInt2 = if (isOnlyOneToX && randInt1 != 1) {
                1
            } else {
                range.random()
            }
            if (randInt1.toDouble() / randInt2 > maxRatio || randInt2.toDouble() / randInt1 > maxRatio) {
                continue
            }
            for (j in 8 downTo 1) {
                if (randInt1 % j == 0 && randInt2 % j == 0) {
                    randInt1 /= j
                    randInt2 /= j
                }
            }
            areRatiosValid = true
        }
        return GridItemModel(pos, randInt1, randInt2)
    }

    override fun toString(): String {
        return "Game(nameOfGame='$nameOfGame', resCount=$resCount, resNames=$resNames, maxRatio=$maxRatio, isOnlyOneToX=$isOnlyOneToX), tables=$tables"
    }

    fun getGameDTO(): GameDTO {
        return GameDTO(
            this.nameOfGame,
            this.resCount,
            this.resNames,
            this.maxRatio,
            this.isOnlyOneToX,
            tables
        )
    }

    fun updateSingleRatio(gridItem: GridItemModel, phase: String) {
        val gridItemOpposite = gridItem.getItemOpposite()
        val map: MutableMap<String, GridItemModel> = tables[phase] ?: mutableMapOf()
        map[gridItem.intPos.toString()] = gridItem
        map[gridItemOpposite.intPos.toString()] = gridItemOpposite
        tables[phase] = map
        //MyManager.reUploadGame()
        MyManager.updateSingleRatio(gridItem, phase)

    }

    fun createNewPhaseTable(phase: String): MutableMap<String, GridItemModel> {
        var tableAct: MutableMap<String, GridItemModel> = mutableMapOf<String, GridItemModel>()
        //val tableAct: MutableMap<String, String> = mutableMapOf<String, String>()

        for (i in resCount..((resCount + 1) * (resCount + 1))) {
            if (Constants.isClickableGridItem(i) && !tableAct.containsKey(i.toString())) {
                val item: GridItemModel = generateOneItem(i)
                val itemOpposite = item.getItemOpposite()
                tableAct[item.intPos.toString()] = item
                tableAct[itemOpposite.intPos.toString()] = itemOpposite


            }
        }
        tables[phase] = tableAct
        return tableAct
    }

    fun getResRatio(phase: String, pos: Int, isRow: Boolean): Int {
        return if (isRow)
            tables[phase]!![pos.toString()]!!.res1Val
        else
            tables[phase]!![pos.toString()]!!.res2Val
    }

}