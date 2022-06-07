package com.example.vma_project_2022_trade_game.data

import com.example.vma_project_2022_trade_game.GridItemModel

class GameDTO() {
    var nameOfGame: String = ""
    var resCount: Int = 0
    var resNames: Map<String, String> = mapOf()
    var maxRatio: Int = 0
    var isOnlyOneToX: Boolean = false
    var tables: MutableMap<String, MutableMap<String, GridItemModel>> = mutableMapOf()

    constructor(nameOfGame: String, resCount: Int,resNames: Map<String, String>, maxRatio: Int, isOnlyOneToX: Boolean, tables: MutableMap<String, MutableMap<String, GridItemModel>>) : this() {
        this.nameOfGame = nameOfGame
        this.resCount = resCount
        this.resNames = resNames
        this.maxRatio = maxRatio
        this.isOnlyOneToX = isOnlyOneToX
        this.tables = tables
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameDTO

        if (nameOfGame != other.nameOfGame) return false
        if (resCount != other.resCount) return false
        if (resNames != other.resNames) return false
        if (maxRatio != other.maxRatio) return false
        if (isOnlyOneToX != other.isOnlyOneToX) return false
        if (tables != other.tables) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nameOfGame.hashCode()
        result = 31 * result + resCount
        result = 31 * result + resNames.hashCode()
        result = 31 * result + maxRatio
        result = 31 * result + isOnlyOneToX.hashCode()
        result = 31 * result + tables.hashCode()
        return result
    }

    override fun toString(): String {
        return "GameDTO(nameOfGame='$nameOfGame', resCount=$resCount, resNames=$resNames, maxRatio=$maxRatio, isOnlyOneToX=$isOnlyOneToX, tables=$tables)"
    }


}

fun GameDTO.toGame(): Game {
    return Game(
        this.nameOfGame,
        this.resCount,
        this.resNames,
        this.maxRatio,
        this.isOnlyOneToX,
        tables
    )
}