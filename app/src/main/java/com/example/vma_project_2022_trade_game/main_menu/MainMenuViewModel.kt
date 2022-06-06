package com.example.vma_project_2022_trade_game.main_menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vma_project_2022_trade_game.Game
import com.example.vma_project_2022_trade_game.GameDTO
import com.example.vma_project_2022_trade_game.toGame
import com.google.firebase.firestore.FirebaseFirestore
import java.util.logging.Logger

class MainMenuViewModel : ViewModel() {

    val LOG = Logger.getLogger(this.javaClass.name)

    private val _games = MutableLiveData<List<Game>?>()
    val games: LiveData<List<Game>?>
        get() = _games

    init {
        FirebaseFirestore.getInstance().collection("games")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    _games.value = null
                    return@addSnapshotListener
                }
                _games.value = value?.map { doc -> doc.toObject(GameDTO::class.java).toGame() }
            }
    }

}