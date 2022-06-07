package com.example.vma_project_2022_trade_game.playing_game

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.vma_project_2022_trade_game.*
import com.example.vma_project_2022_trade_game.data.Constants
import com.example.vma_project_2022_trade_game.data.Game
import com.example.vma_project_2022_trade_game.databinding.FragmentResourceSetupBinding
import java.util.logging.Logger


class ResourceSetupFragment : Fragment(R.layout.fragment_resource_setup) {
    lateinit var sp: SharedPreferences


    lateinit var editor: SharedPreferences.Editor
    val phase: String get() = sp.getInt("phase", 0).toString()

    val LOG = Logger.getLogger(this.javaClass.name)
    val gameAct get() = MyManager.gameActual

    //val database =
    //    FirebaseDatabase.getInstance("https://tradegametoolvma2022-default-rtdb.europe-west1.firebasedatabase.app").getReference("Games")

    var _binding: FragmentResourceSetupBinding? = null
    val binding get() = _binding!!
    //var resNamesList: ArrayList<EditText> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        sp = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        editor = sp.edit()

        //Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_resource_setup, container, false)
        //val resCount: EditText = view.findViewById(R.id.resNumberTextField)

        //val recyclerView: RecyclerView = view.findViewById(R.id.resNamesRecyclerView)

        //val adapter = ResNamesAdapter()

        _binding = FragmentResourceSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragmentData()
        binding.onlyOneToXCheckbox.setOnClickListener {
            Toast.makeText(activity, "checkbox clicked", Toast.LENGTH_SHORT).show()
        }
        binding.nextButton.setOnClickListener {
            saveInitialGame()
            //editor.putBoolean("edit", false)
            editor.putInt("phase", 1)
            editor.commit()
            Navigation.findNavController(view)
                .navigate(R.id.action_resourceSetupFragment_to_phaseEditFragment)

        }
    }

    private fun initFragmentData() {
        binding.nameOfGameTextField.setText(gameAct.nameOfGame)
        binding.onlyOneToXCheckbox.isChecked = gameAct.isOnlyOneToX
        binding.maxRatioTextField.setText(gameAct.maxRatio.toString())
        binding.resNamesTextField.setText(Constants.gerResourcesForTextField(gameAct.resNames))
    }

    private fun saveInitialGame() {
        val resNames: List<String> = binding.resNamesTextField.text.toString().split(',')
        resNames.forEach { it.trim() }
        // convert to map
        val resNamesMap: Map<String, String> =
            resNames.associateBy { resNames.indexOf(it).toString() }

        var tables: MutableMap<String, MutableMap<String, GridItemModel>> = mutableMapOf()
        if (resNames.size == gameAct.resCount) {
            LOG.warning("same number of Resources -> leave the old tables")
            tables = gameAct.tables
        }
        val game = Game(
            binding.nameOfGameTextField.text.toString(),
            resNames.size,
            resNamesMap,
            Integer.parseInt(binding.maxRatioTextField.text.toString()),
            binding.onlyOneToXCheckbox.isChecked,
            tables
        )

        LOG.warning("ACTUAL LOG ???  ${game.nameOfGame}")
        print("Game created and db tries to upload")
        /*val database =
            FirebaseDatabase.getInstance("https://tradegametoolvma2022-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Games")
        database.child(game.nameOfGame).setValue(game).addOnSuccessListener {
            MyManager.LOG.warning("OnSuccessListener -  uploaded ${game.nameOfGame}")*/
        MyManager.createNewGame(game)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}