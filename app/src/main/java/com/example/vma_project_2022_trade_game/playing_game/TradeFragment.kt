package com.example.vma_project_2022_trade_game.playing_game

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.vma_project_2022_trade_game.MyManager
import com.example.vma_project_2022_trade_game.R
import com.example.vma_project_2022_trade_game.data.Constants
import com.example.vma_project_2022_trade_game.data.TradeDataModel
import com.example.vma_project_2022_trade_game.databinding.FragmentTradeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [TradeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TradeFragment : Fragment() {


    var _binding: FragmentTradeBinding? = null
    val binding get() = _binding!!
    val gameAct get() = MyManager.gameActual
    val columnsCount get() = gameAct.resCount + 1

    lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    val phase: String get() = sp.getInt("phase", 0).toString()
    private val KEY_TRADE_MODEL = "tradeDataModelBundle"
    private val KEY_TRADE_MADE = "tradeMade"
    lateinit var tradeDataModel: TradeDataModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tradeDataModel =
                Constants.tradeDataModelFromString(it.getString(KEY_TRADE_MODEL) ?: "0,-1,0,0,0,0")

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TRADE_MODEL, tradeDataModel.toBundleString())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sp = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        editor = sp.edit()

        _binding = FragmentTradeBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            tradeDataModel =
                Constants.tradeDataModelFromString(it.getString(KEY_TRADE_MODEL) ?: "0,-1,0,0,0,0")
            if (tradeDataModel.res1RatioVal == -1) {
                savedInstanceState.let {
                    val strFromBundle = savedInstanceState?.getString(KEY_TRADE_MODEL)
                    if (strFromBundle == null) {
                        tradeDataModel = TradeDataModel(0, -1, 0, 0, 0, 0)

                    } else {
                        tradeDataModel =
                            Constants.tradeDataModelFromString(strFromBundle)
                    }

                }
            }
        }
        println(tradeDataModel.toString())

        setTextViews()

        binding.swapButton.setOnClickListener {
            tradeDataModel = tradeDataModel.getOpposite()
            setTextViews()

        }
        binding.sellAllButton.setOnClickListener {
            binding.sellTextField.setText((tradeDataModel.res1ownedValue).toString())
            showTrade()
        }

        binding.makeTradeButton.setOnClickListener {
            val bundle = Bundle()

            val res1Diff = if (binding.sellTextField.text.toString()
                    .isDigitsOnly()
            ) "-" + binding.sellTextField.text.toString() else "0"

            val res2Diff = if (binding.gotTextField.text.toString()
                    .isDigitsOnly()
            ) binding.gotTextField.text.toString() else "0"
            bundle.putString(
                KEY_TRADE_MADE,
                "${tradeDataModel.res1Id},$res1Diff,${tradeDataModel.res2Id},$res2Diff"
            )
            Navigation.findNavController(it)
                .navigate(R.id.action_tradeFragment_to_playingGameFragment, bundle)
        }
    }

    private fun setTextViews() {
        binding.resOneTradeTextView.text = gameAct.resNames[(tradeDataModel.res1Id).toString()]
        binding.resTwoTradeTextView.text = gameAct.resNames[(tradeDataModel.res2Id).toString()]
    }

    private fun showTrade() {
        if (!binding.sellTextField.text.toString().isDigitsOnly()) {
            Toast.makeText(requireContext(), "zadaj počet prdávanej suroviny", Toast.LENGTH_LONG)
                .show()
        } else {
            val sellAmount = Integer.parseInt(binding.sellTextField.text.toString())
            val gotAmount = Constants.makeTrade(
                sellAmount,
                tradeDataModel.res1RatioVal,
                tradeDataModel.res2RatioVal
            )
            binding.gotTextField.setText(gotAmount.toString())
        }

    }


}