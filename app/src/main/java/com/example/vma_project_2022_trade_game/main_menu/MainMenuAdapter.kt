package com.example.vma_project_2022_trade_game.main_menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vma_project_2022_trade_game.data.Game
import com.example.vma_project_2022_trade_game.R

class MainMenuAdapter(var games: List<Game>, val recyclerViewCallback: RecyclerViewCallback) :
    RecyclerView.Adapter<MainMenuAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMenuAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainMenuAdapter.ViewHolder, position: Int) {
        holder.gameNameText.text = games[position].nameOfGame
        holder.playGameButton.setOnClickListener {
            recyclerViewCallback.playGame(games[position])
        }
        holder.editGameButton.setOnClickListener {
            recyclerViewCallback.editGame(games[position])
        }

    }

    override fun getItemCount(): Int {
        return games.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameNameText: TextView = itemView.findViewById(R.id.gameName)
        val playGameButton: Button = itemView.findViewById(R.id.playGameButton)
        val editGameButton: Button = itemView.findViewById(R.id.editGameButton)
    }
}

interface RecyclerViewCallback {

    fun playGame(game: Game)

    fun editGame(game: Game)
}