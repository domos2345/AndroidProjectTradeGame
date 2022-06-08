package com.example.vma_project_2022_trade_game.playing_game

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.vma_project_2022_trade_game.*
import com.example.vma_project_2022_trade_game.data.Constants
import com.example.vma_project_2022_trade_game.data.TradeDataModel
import com.example.vma_project_2022_trade_game.databinding.FragmentPlayingGameBinding
import java.util.logging.Logger


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
    var resValues: MutableMap<Int, Int> =
        Constants.generateResValues(gameAct.resNames) as MutableMap<Int, Int>
    private val KEY_TRADE_MODEL = "tradeDataModelBundle"
    private val KEY_TRADE_MADE = "tradeMade"
    val LOG = Logger.getLogger(this.javaClass.name)

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
        val args = this.arguments
        val inputData = args?.get("data")
        val inputDataOfTrade = args?.getString(KEY_TRADE_MADE)

        //if (this.arguments?.getString("data") != null) {
        LOG.warning(inputData.toString() + "  -> Bundle from data")
        if (inputData != null) {
            resValues =
                Constants.getResValuesFromString(inputData.toString()) as MutableMap<Int, Int>
        }
        println("" + resValues + "  :  resAndValues")
        if (inputDataOfTrade != null) {
            val list = inputDataOfTrade.split(",")
            var newRes1: Int = resValues[Integer.parseInt(list[0])]!!
            newRes1 += (Integer.parseInt(list[1]))
            resValues[Integer.parseInt(list[0])] =
                (newRes1)

            var newRes2: Int = resValues[Integer.parseInt(list[2])]!!
            newRes1 += (Integer.parseInt(list[3]))
            resValues[Integer.parseInt(list[0])] =
                (newRes2)
        }
        println("" + resValues + "  :  resAndValues")


        // } else resValues = Constants.generateResValues()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillTableInitData()
        adapter = GridAdapterPlayMode(
            requireActivity(), listOfGridItems
        )
        uploadNewPhase()
        //GridView gridView = (GridView)findViewById(...);
        //ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(width,height);
        //gridView.setLayoutParams(lp);
        val gridView = binding.gridviewInGame
        val lp: ViewGroup.LayoutParams = gridView.layoutParams
        lp.height = (gameAct.resCount + 1) * 80
        gridView.layoutParams = lp
        binding.gridviewInGame.adapter = adapter
        binding.gridviewInGame.numColumns = columnsCount
        adapter.notifyDataSetChanged()
        // val args: Bundle


        binding.resourcesActTextView.text = Constants.createResActText(resValues)
        binding.nextPhaseButtonInGame.setOnClickListener {
            incrementPhase()
            uploadNewPhase()
        }
        binding.previousPhaseButtonInGame.setOnClickListener {
            decrementPhase()
            uploadNewPhase()
        }

        binding.setResourcesButton.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_playingGameFragment_to_setResourcesInGameFragment)
        }

        binding.gridviewInGame.setOnItemClickListener { _, view, i, l ->
            if (Constants.isClickableGridItem(i)) {
                val row = Constants.rowResIdFromPos(i)
                val col = Constants.colResIdFromPos(i)
                val tradeDataModel = TradeDataModel(
                    row, gameAct.getResRatio(phase, i, true),
                    resValues[row]!!, col, gameAct.getResRatio(phase, i, false), resValues[col]!!
                )
                val bundle = Bundle()
                bundle.putString(KEY_TRADE_MODEL, tradeDataModel.toBundleString())
                Navigation.findNavController(view)
                    .navigate(
                        R.id.action_playingGameFragment_to_tradeFragment,
                        bundle
                    )
            }

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