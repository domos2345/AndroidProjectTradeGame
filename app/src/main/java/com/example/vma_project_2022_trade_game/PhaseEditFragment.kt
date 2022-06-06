package com.example.vma_project_2022_trade_game

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.vma_project_2022_trade_game.databinding.FragmentPhaseEditBinding
import java.lang.IllegalStateException


class PhaseEditFragment : Fragment(R.layout.fragment_phase_edit) {

    var _binding: FragmentPhaseEditBinding? = null
    val binding get() = _binding!!
    val game get() = MyManager.gameActual


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
        var sharedpreferences: SharedPreferences =
            this.requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        var listOfGridItems =
            MutableList((MyManager.resCount + 1) * (MyManager.resCount + 1)) { "X" }
        val columnsCount = MyManager.resCount + 1
        for (i in 1 until (columnsCount)) {
            listOfGridItems[i] =
                MyManager.gameActual.resNames[(i - 1).toString()].toString()
        }

        for (i in 1 until (columnsCount)) {
            listOfGridItems[i * columnsCount] =
                MyManager.gameActual.resNames[(i - 1).toString()].toString()
        }
        game.tables["1"]!!.values.forEach {
            listOfGridItems[it.intPos] = it.gridItemText()
        }


        val adapter = GridAdapter(
            requireActivity(), listOfGridItems

        )
        binding.gridview.adapter = adapter

        binding.gridview.numColumns = columnsCount
        //view.findViewById<GridView>(R.id.gridview).numColumns = columnsCount
        binding.nameOfPhase.text = "Fáza 1 "
        //listOfGridItems[6] = "work?"
        adapter.notifyDataSetChanged()
        /*for (i in 0..columnsCount) {
            binding.gridview[1 + (i * columnsCount + 1)].setBackgroundResource(R.color.black)
        }*/


        binding.gridview.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->

            //view1.findViewById<TextView>(R.id.gridItemText).text = "1:3"
            //listOfGridItems[i] = "clicked"
            if (Constants.isClickableGridItem(i)) {
                Toast.makeText(activity, "CLICKABLE item", Toast.LENGTH_SHORT).show()
                val dialogObject = ChangeItemDialog(listOfGridItems[i], i)
                dialogObject.show(requireFragmentManager(), "dialogGridItem")
            } else
                Toast.makeText(activity, "grid item not CLICKABLE", Toast.LENGTH_SHORT).show()
            // val dialogObject = ChangeItemDialog(listOfGridItems[i], i)

            //adapter.notifyDataSetChanged()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    class ChangeItemDialog(val itemText: String, val pos: Int) : DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)
                val inflater = requireActivity().layoutInflater
                var dialogView = inflater.inflate(R.layout.change_grid_item_dialog, null)
                val editTextRes1 = dialogView.findViewById<EditText>(R.id.resOneTextField)
                val editTextRes2 = dialogView.findViewById<EditText>(R.id.resTwoTextField)

                editTextRes1.hint = Constants.getRes1NameFromPos(
                    pos
                )

                editTextRes2.hint = Constants.getRes2NameFromPos(
                    pos
                )
                if (Constants.isClickableGridItem(pos, MyManager.gameActual.resCount)) {
                    val texts = itemText.split(':')
                    editTextRes1.setText(texts[0].trim())
                    editTextRes2.setText(texts[1].trim())
                }
                builder.setView(dialogView).setPositiveButton(
                    "Uložiť",
                    DialogInterface.OnClickListener() { dialog, which ->
                        println("ulozit button dialog")
                    })
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }

}