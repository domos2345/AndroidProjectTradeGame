package com.example.vma_project_2022_trade_game

class GameBasicData(
    val nameOfGame: String,
    val resCount: Int,
    val resNames: Map<String, String>,
    val maxRatio: Int,
    val isOnlyOneToX: Boolean,
    val tablesToDb: MutableMap<String, Map<String, GridItemModel>>,
    val testMap: Map<String, GridItemModel> = mapOf<String, GridItemModel>(
        "1" to GridItemModel(
            1,
            2,
            3
        ), "2" to GridItemModel(1, 2, 3)
    )
) {


    override fun toString(): String {
        return "Game(nameOfGame='$nameOfGame', resCount=$resCount, resNames=$resNames, maxRatio=$maxRatio, isOnlyOneToX=$isOnlyOneToX)"
    }


}