package com.example.vma_project_2022_trade_game.data

import com.example.vma_project_2022_trade_game.GridItemModel

data class TradeDataModel(
    val res1Id: Int,
    val res1RatioVal: Int,
    val res1ownedValue: Int,
    val res2Id: Int,
    val res2RatioVal: Int,
    val res2ownedValue: Int
) {
    fun getOpposite(): TradeDataModel {
        return (TradeDataModel(
            res2Id,
            res2RatioVal,
            res2ownedValue,
            res1Id,
            res1RatioVal,
            res1ownedValue
        ))
    }

    fun toBundleString(): String {
        return "$res1Id,$res1RatioVal,$res1ownedValue,$res2Id,$res2RatioVal,$res2ownedValue"
    }
}