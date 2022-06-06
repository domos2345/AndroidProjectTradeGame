package com.example.vma_project_2022_trade_game

class Game(
    val nameOfGame: String,
    val resCount: Int,
    val resNames: Map<String, String>,
    val maxRatio: Int,
    val isOnlyOneToX: Boolean,
    val testInConstructor: Int = 150
) {

    //val tables: MutableMap<String, Map<String, GridItemModel>> = mutableMapOf()
    val testOut: Int = 123

    //val tables: ArrayList<Map<String, GridItemModel>> = arrayListOf()
    val tables: MutableMap<String, Map<String, GridItemModel>> =
        mutableMapOf<String, Map<String, GridItemModel>>()
    //val tables: MutableMap<String, Map<String, String>> = mutableMapOf()


    fun generateGridItems(phase: Int) {
        val tableAct: MutableMap<String, GridItemModel> = mutableMapOf<String, GridItemModel>()
        //val tableAct: MutableMap<String, String> = mutableMapOf<String, String>()
        val map = mutableMapOf<Int, Any?>()
        for (i in resCount..((resCount + 1) * (resCount + 1))) {
            if (Constants.isClickableGridItem(i) && !tableAct.containsKey(i.toString())) {
                val item: GridItemModel = generateOneItem(i)
                val itemOpposite = item.getItemOpposite()
                tableAct[item.intPos.toString()] = item
                tableAct[itemOpposite.intPos.toString()] = itemOpposite
                tableAct.forEach {
                    println(it.key.toString() + " val - " + it.value.toString())
                }
                println("END OF PRINT")


            }
        }
        tables[phase.toString()] = tableAct
        //tables.add(tableAct)
    }

    private fun generateOneItem(pos: Int): GridItemModel {
        var areRatiosValid = false
        var randInt1 = (1..8).random()
        var randInt2 = (1..8).random()
        while (!areRatiosValid) {
            randInt1 = (1..8).random()
            randInt2 = if (isOnlyOneToX && randInt1 != 1) {
                1
            } else {
                (1..8).random()
            }
            if (randInt1 * 1.0 / randInt2 > maxRatio || randInt2 * 1.0 / randInt1 > maxRatio) {
                continue
            }
            for (j in 9 downTo 1) {
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
        return "Game(nameOfGame='$nameOfGame', resCount=$resCount, resNames=$resNames, maxRatio=$maxRatio, isOnlyOneToX=$isOnlyOneToX, testInConstructor=$testInConstructor, testOut=$testOut, tables=$tables)"
    }

    fun makeTablesForDb(tables: Map<String, Map<String, GridItemModel>>): MutableMap<String, Map<String, String>> {
        val finalTable: MutableMap<String, Map<String, String>> = mutableMapOf()


        tables.entries.forEach { phase ->
            val tableTemp: MutableMap<String, String> = mutableMapOf()
            phase.value.entries.forEach { gridRatios ->
                tableTemp[gridRatios.key] = gridRatios.value.toString()
            }
            finalTable[phase.key] = tableTemp
        }
        return finalTable
    }

    fun getBasicDataGame(): GameBasicData {
        return GameBasicData(
            this.nameOfGame,
            this.resCount,
            this.resNames,
            this.maxRatio,
            this.isOnlyOneToX,
            makeTablesForDb(tables)
        )
    }

}