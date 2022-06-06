package com.example.vma_project_2022_trade_game

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import java.util.logging.Logger


object MyManager {

    val resCount get() = gameActual.resCount

    /*var database =
        FirebaseDatabase.getInstance("https://tradegametoolvma2022-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("Games")*/

    val LOG = Logger.getLogger(this.javaClass.name)

    lateinit var gameActual: Game

    init {
        gameActual = Game("gen", 1, emptyMap(), 4, false)
        /* database =
             FirebaseDatabase.getInstance("https://tradegametoolvma2022-default-rtdb.europe-west1.firebasedatabase.app")
                 .getReference("Games")*/
    }

    fun createNewGame(game: Game) {
        gameActual =
            Game(game.nameOfGame, game.resCount, game.resNames, game.maxRatio, game.isOnlyOneToX)
        println(gameActual.toString())
        gameActual.generateGridItems(1)
        println(gameActual.toString())
        println("" + Constants.isClickableGridItem(5) + " is 5 clickable???")
        //LOG.warning(gameActual.tables[0].entries.size.toString())

        LOG.warning("createNewGame running")

        val db = FirebaseFirestore.getInstance()
        db.collection("games").document(gameActual.nameOfGame).set(gameActual.getBasicDataGame())
            .addOnCompleteListener {

                LOG.warning("ON COMPLETE LISTENER - Game ${game.nameOfGame} uploaded")
                //uploadTables(gameActual.tables)
            }

        //db.collection("games_tables").document(gameActual.nameOfGame).set(gameActual.tables["0"]!!.values)

        //println(gameActual.tables["1"].toString() + " GAME CREATED???")
        /* database.child(game.nameOfGame).setValue(gameActual).addOnCompleteListener {
             LOG.warning("OnCompleteListener -  saved ${game.nameOfGame}")

         }.addOnSuccessListener {
             LOG.warning("OnCompleteListener -  uploaded ${game.nameOfGame}")
         }*/

    }

    fun uploadTables(tables: Map<String, Map<String, GridItemModel>>) {
        val db = FirebaseFirestore.getInstance()
        db.collection("games").document(gameActual.nameOfGame).collection("tables")
            .document("phase1").collection("it.key").document("it.key").set("it.value")

        // tables.entries.forEach { table ->

        //   table.value.entries.forEach {


        //db.collection("games_tables").document(gameActual.nameOfGame).set(GridItemModel(2,3,4))
    }


}

