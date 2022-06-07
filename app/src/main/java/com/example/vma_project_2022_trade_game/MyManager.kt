package com.example.vma_project_2022_trade_game

import com.example.vma_project_2022_trade_game.data.Game
import com.google.firebase.firestore.FirebaseFirestore
import java.util.logging.Logger


object MyManager {

    val resCount get() = gameActual.resCount
    val db = FirebaseFirestore.getInstance()
    val LOG = Logger.getLogger(this.javaClass.name)

    var gameActual: Game =
        Game("Moja Hra", 3, mapOf("0" to "drevo", "1" to "kameň", "2" to "železo"), 4, false)
    val genericGame: Game =
        Game("Moja Hra", 3, mapOf("0" to "drevo", "1" to "kameň", "2" to "železo"), 4, false)

    init {
        //gameActual =


    }

    fun createNewGame(game: Game) {
        gameActual =
            Game(
                game.nameOfGame,
                game.resCount,
                game.resNames,
                game.maxRatio,
                game.isOnlyOneToX,
                game.tables
            )
        println(gameActual.toString())
        if (gameActual.tables.isEmpty()) {
            gameActual.generateGridItems(1)
        }

        println(gameActual.toString())

        LOG.warning("createNewGame running")
        db.collection("games").document(gameActual.nameOfGame).set(gameActual.getGameDTO())
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

        db.collection("games").document(gameActual.nameOfGame).collection("tables")
            .document("phase1").collection("it.key").document("it.key").set("it.value")

        // tables.entries.forEach { table ->

        //   table.value.entries.forEach {


        //db.collection("games_tables").document(gameActual.nameOfGame).set(GridItemModel(2,3,4))
    }

    fun reUploadGame() {
        db.collection("games").document(gameActual.nameOfGame).set(gameActual.getGameDTO())
    }

    fun updateSingleRatio(gridItem: GridItemModel, phase: String) {
        val gridItemOpposite = gridItem.getItemOpposite()
        db.collection("games").document(gameActual.nameOfGame).update(
            mapOf(
                "tables.$phase.${gridItem.intPos}" to gridItem,
                "tables.$phase.${gridItemOpposite.intPos}" to gridItemOpposite

            )
        )
    }

    fun uploadTable(table: MutableMap<String, GridItemModel>, phase: String) {
        db.collection("games").document(gameActual.nameOfGame).update(
            mapOf(
                "tables.$phase" to table
            )
        )

    }


}

