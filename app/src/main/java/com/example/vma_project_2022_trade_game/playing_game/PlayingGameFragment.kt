package com.example.vma_project_2022_trade_game.playing_game

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.vma_project_2022_trade_game.*
import com.example.vma_project_2022_trade_game.databinding.FragmentPhaseEditBinding
import com.example.vma_project_2022_trade_game.databinding.FragmentPlayingGameBinding


class PlayingGameFragment : Fragment(R.layout.fragment_playing_game) {
    var _binding: FragmentPlayingGameBinding? = null
    val binding get() = _binding!!

    val gameAct get() = MyManager.gameActual
    val columnsCount get() = gameAct.resCount + 1

    lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    val phase: String get() = sp.getInt("phase", 0).toString()

    lateinit var listOfGridItems: MutableList<String>
    lateinit var adapter: GridAdapterPlayMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sp = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        editor = sp.edit()

        // Inflate the layout for this fragment
        _binding = FragmentPlayingGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillTableInitData()
        adapter = GridAdapterPlayMode(
            requireActivity(), listOfGridItems
        )
        uploadNewPhase()

        binding.gridviewInGame.adapter = adapter
        binding.gridviewInGame.numColumns = columnsCount
        adapter.notifyDataSetChanged()



        binding.nextPhaseButtonInGame.setOnClickListener {
            incrementPhase()
            uploadNewPhase()
        }
        binding.previousPhaseButtonInGame.setOnClickListener {
            decrementPhase()
            uploadNewPhase()
        }
    }


    private fun fillTableInitData() {
        listOfGridItems =
            MutableList((MyManager.resCount + 1) * (MyManager.resCount + 1)) { "X" }

        for (i in 1 until (columnsCount)) {
            listOfGridItems[i] =
                MyManager.gameActual.resNames[(i - 1).toString()].toString()
        }

        for (i in 1 until (columnsCount)) {
            listOfGridItems[i * columnsCount] =
                MyManager.gameActual.resNames[(i - 1).toString()].toString()
        }
    }

    private fun uploadNewPhase() {
        binding.nameOfPhaseInGame.text = "Fáza $phase"
        val table: MutableMap<String, GridItemModel> = if (gameAct.tables.containsKey(phase)) {
            gameAct.tables[phase]!!
        } else {
            gameAct.createNewPhaseTable(phase)

        }

        table.values.forEach {
            listOfGridItems[it.intPos] = it.gridItemText()
        }
        adapter.notifyDataSetChanged()
        MyManager.uploadTable(table, phase)
        val hasPrevious = hasPreviousPhase()
        val hasNext = hasNextPhase()
        binding.previousPhaseButtonInGame.isClickable = hasPrevious
        binding.previousPhaseButtonInGame.visibility =
            if (hasPrevious) View.VISIBLE else View.INVISIBLE

        binding.nextPhaseButtonInGame.isClickable = hasNext
        binding.nextPhaseButtonInGame.visibility = if (hasNext) View.VISIBLE else View.INVISIBLE

    }

    private fun incrementPhase() {
        editor.putInt("phase", sp.getInt("phase", 0) + 1)
        editor.apply()
    }

    private fun decrementPhase() {
        editor.putInt("phase", sp.getInt("phase", 0) - 1)
        editor.apply()
    }

    private fun hasPreviousPhase(): Boolean {
        return gameAct.tables.containsKey((sp.getInt("phase", 0) - 1).toString())
    }

    private fun hasNextPhase(): Boolean {
        return gameAct.tables.containsKey((sp.getInt("phase", 0) + 1).toString())
    }


}