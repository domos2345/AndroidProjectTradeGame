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


class PhaseEditFragment : Fragment(R.layout.fragment_phase_edit), GridItemUpdate {

    var _binding: FragmentPhaseEditBinding? = null
    val binding get() = _binding!!
    val game get() = MyManager.gameActual
    val columnsCount get() = game.resCount + 1
    val sp get() = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val editor get() = sp.edit()
    val phase: String get() = sp.getInt("phase", 0).toString()


    lateinit var listOfGridItems: MutableList<String>

    lateinit var adapter: GridAdapter
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

        fillTableInitData()

        adapter = GridAdapter(
            requireActivity(), listOfGridItems

        )
        // FILL WITH ACTUAL TABLE DATA
        uploadNewPhase()


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
                val dialogObject = ChangeItemDialog(
                    game.tables["1"]?.get(i.toString()) ?: GridItemModel(0, 0, 0), this
                )
                dialogObject.show(requireFragmentManager(), "dialogGridItem")
            } else
                Toast.makeText(activity, "grid item not CLICKABLE", Toast.LENGTH_SHORT).show()
            // val dialogObject = ChangeItemDialog(listOfGridItems[i], i)

            //adapter.notifyDataSetChanged()

        }
        binding.newPhaseButton.setOnClickListener {
            editor.putInt("phase", sp.getInt("phase", 0) + 1)
            Toast.makeText(activity, phase, Toast.LENGTH_SHORT).show()
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

        val table: MutableMap<String, GridItemModel> = if (game.tables.containsKey(phase)) {
            game.tables[phase]!!
        } else {
            game.createNewPhaseTable(phase)
        }

        table.values.forEach {
            listOfGridItems[it.intPos] = it.gridItemText()
        }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    class ChangeItemDialog(val gridItem: GridItemModel, val gridItemUpdate: GridItemUpdate) :
        DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)
                val inflater = requireActivity().layoutInflater
                var dialogView = inflater.inflate(R.layout.change_grid_item_dialog, null)
                val editTextRes1 = dialogView.findViewById<EditText>(R.id.resOneTextField)
                val editTextRes2 = dialogView.findViewById<EditText>(R.id.resTwoTextField)

                editTextRes1.hint = Constants.getRes1NameFromPos(
                    gridItem.intPos
                )

                editTextRes2.hint = Constants.getRes2NameFromPos(
                    gridItem.intPos
                )
                if (Constants.isClickableGridItem(gridItem.intPos)) {

                    editTextRes1.setText(gridItem.res1Val.toString())
                    editTextRes2.setText(gridItem.res2Val.toString())
                }
                builder.setView(dialogView).setPositiveButton(
                    "Uložiť", DialogInterface.OnClickListener
                    { dialog, which ->
                        gridItemUpdate.updateGridItem(
                            GridItemModel(
                                gridItem.intPos,
                                Integer.parseInt(editTextRes1.text.toString()),
                                Integer.parseInt(editTextRes2.text.toString())
                            )
                        )
                        println("ulozit button dialog")
                        getDialog()?.cancel()
                    })
                    .setNegativeButton(
                        "Zrušiť",
                        DialogInterface.OnClickListener { dialogInterface, i ->

                            dialog?.cancel()
                        })

                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }

    override fun updateGridItem(gridItem: GridItemModel) {
        //
        //
        // TODO
        game.updateSingleRatio(gridItem, phase)
        val gridItemOpposite = gridItem.getItemOpposite()
        //game.updateSingleRatio(gridItemOpposite, phase)
        listOfGridItems[gridItem.intPos] = gridItem.gridItemText()
        listOfGridItems[gridItemOpposite.intPos] = gridItemOpposite.gridItemText()
        adapter.notifyDataSetChanged()
    }

}

interface GridItemUpdate {
    fun updateGridItem(gridItem: GridItemModel)
}