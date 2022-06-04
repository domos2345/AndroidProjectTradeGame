package com.example.vma_project_2022_trade_game

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import com.example.vma_project_2022_trade_game.databinding.FragmentMainMenuBinding
import com.example.vma_project_2022_trade_game.databinding.FragmentPhaseEditBinding
import com.example.vma_project_2022_trade_game.databinding.FragmentResourceSetupBinding


class PhaseEditFragment : Fragment(R.layout.fragment_phase_edit) {

    var _binding: FragmentPhaseEditBinding? = null
    val binding get() = _binding!!

    var gridItems: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentPhaseEditBinding.inflate(inflater, container, false)
        return binding.root

        //val view = inflater.inflate(R.layout.fragment_phase_edit, container, false)
        //return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var listOfGridItems = MutableList((MyManager.resCount + 1) * (MyManager.resCount + 1)) { "X" }
        val columnsCount = MyManager.resCount + 1
        for (i in 1 until (columnsCount)) {
            listOfGridItems[i] =
                MyManager.gameActual.resNames[(i - 1).toString()].toString()
        }

        for (i in 1 until (columnsCount)) {
            listOfGridItems[i*columnsCount] =
                MyManager.gameActual.resNames[(i - 1).toString()].toString()
        }

        val adapter = GridAdapter(
            requireActivity(), listOfGridItems

        )
        binding.gridview.adapter = adapter



        binding.gridview.numColumns = columnsCount
        //view.findViewById<GridView>(R.id.gridview).numColumns = columnsCount
        binding.nameOfPhase.text = "Fáza 1 "
        listOfGridItems[6] = "work?"
        adapter.notifyDataSetChanged()


        binding.gridview.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            Toast.makeText(activity, "grid item clicked", Toast.LENGTH_SHORT).show()
            view1.findViewById<TextView>(R.id.gridItemText).text = "1:3"

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}