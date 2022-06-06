package com.example.vma_project_2022_trade_game

data class GameDTO(
    val nameOfGame: String,
    val resCount: Int,
    val resNames: Map<String, String>,
    val maxRatio: Int,
    val isOnlyOneToX: Boolean,
    val tables: MutableMap<String, MutableMap<String, GridItemModel>>,
)