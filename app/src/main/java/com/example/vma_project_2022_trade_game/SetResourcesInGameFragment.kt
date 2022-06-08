package com.example.vma_project_2022_trade_game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.vma_project_2022_trade_game.data.Constants
import com.example.vma_project_2022_trade_game.databinding.FragmentSetResourcesInGameBinding


class SetResourcesInGameFragment :
    Fragment(R.layout.fragment_set_resources_in_game) {
    private val KEY = "bundleData"
    var _binding: FragmentSetResourcesInGameBinding? = null
    val binding get() = _binding!!
    val gameAct get() = MyManager.gameActual
    lateinit var resAndValues: MutableMap<Int, Int>

    lateinit var adapter: SetResourcesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentSetResourcesInGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY, Constants.getResValuesAsString(resAndValues))

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState.let {
            val strFromBundle = it?.getString(KEY)
            if (strFromBundle == null) {
                resAndValues = mutableMapOf()
                for (i in 0 until gameAct.resCount) {
                    resAndValues[i] = 0
                }
            } else {
                resAndValues =
                    Constants.getResValuesFromString(strFromBundle) as MutableMap<Int, Int>
            }

        }


        adapter = SetResourcesAdapter(
            requireActivity(), resAndValues
        )

        binding.resListView.adapter = adapter
        adapter.notifyDataSetChanged()

        for (i in 0 until resAndValues.size)


            binding.resetToZeroButton.setOnClickListener {
                resAndValues.keys.forEach {
                    resAndValues[it] = 0
                }
                adapter.notifyDataSetChanged()
            }
        binding.saveResourcesInGameButton.setOnClickListener {
            val bundle = Bundle()
            println(resAndValues.toString())
            bundle.putString("data", Constants.getResValuesAsString(resAndValues))
            println(bundle.toString() + "   :  bundle")

            Navigation.findNavController(view)
                .navigate(R.id.action_setResourcesInGameFragment_to_playingGameFragment, bundle)
        }
    }

}

class SetResourcesAdapter(val context: Context, val resIdsAndNumbers: MutableMap<Int, Int>) :
    BaseAdapter() {

    override fun getCount(): Int {
        return resIdsAndNumbers.size
    }

    override fun getItem(p0: Int): Any {
        return resIdsAndNumbers[p0]!!
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val number = resIdsAndNumbers[p0]
        var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.resource_text_field_item, null)
        val editText = view.findViewById<EditText>(R.id.resNumberTextField)
        editText.setText(number.toString())
        view.findViewById<TextView>(R.id.resNameTextView).text =
            MyManager.gameActual.resNames[p0.toString()]
        editText.doAfterTextChanged {
            if (it.toString().isDigitsOnly()) {
                resIdsAndNumbers[p0] = Integer.parseInt(it.toString())
            } else {
                resIdsAndNumbers[p0] = 2
            }
        }

        //view.findViewById<TextView>(R.id.gridItemText).setBackgroundResource(R.color.black)
        return view
    }


}

/*interface SetResourcesCallback {

    fun updateActualResources(resValues: Map<Int, Int>)
}
*/

//class PosNameVal(val resId:Int, val name:String,val number: Int)