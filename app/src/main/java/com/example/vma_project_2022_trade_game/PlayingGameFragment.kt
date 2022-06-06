package com.example.vma_project_2022_trade_game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vma_project_2022_trade_game.databinding.FragmentPhaseEditBinding
import com.example.vma_project_2022_trade_game.databinding.FragmentPlayingGameBinding


class PlayingGameFragment : Fragment(R.layout.fragment_playing_game) {
    var _binding: FragmentPlayingGameBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playing_game, container, false)
    }



}