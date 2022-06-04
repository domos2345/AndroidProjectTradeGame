package com.example.vma_project_2022_trade_game

import android.app.Application
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.database.FirebaseDatabase
import java.util.logging.Logger

object MyManager {

    val resCount get() = gameActual.resCount

    val database =
        FirebaseDatabase.getInstance("https://tradegametoolvma2022-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("Games")

    val LOG = Logger.getLogger(this.javaClass.name)

    lateinit var gameActual: Game

    init {
        gameActual = Game("gen",1, emptyMap(),4,false)
    }

    fun createNewGame(game: Game) {
        gameActual = game
        database.child(game.nameOfGame).setValue(game).addOnSuccessListener {
            LOG.warning("OnSuccessListener -  uploaded ${game.nameOfGame}")

            database.child(game.nameOfGame).setValue(game).addOnCompleteListener {
                LOG.warning("OnCompleteListener -  uploaded ${game.nameOfGame}")
            }
        }

    }


}