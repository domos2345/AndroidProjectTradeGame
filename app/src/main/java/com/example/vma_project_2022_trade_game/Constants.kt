package com.example.vma_project_2022_trade_game

object Constants {

    fun fromPosToTupleString(pos:Int, resCount : Int) : String{
        return ""+pos/(resCount+1) +":" + pos%(resCount+1)

    }

    fun fromTupleToPosInt(/*row*/ row:Int, /*column*/ column:Int, resCount: Int) : Int{
        return  row*(resCount+1) + column
    }
}