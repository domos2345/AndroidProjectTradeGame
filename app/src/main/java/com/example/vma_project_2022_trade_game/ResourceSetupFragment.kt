package com.example.vma_project_2022_trade_game

import android.os.Bundle

import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vma_project_2022_trade_game.databinding.FragmentMainMenuBinding
import com.example.vma_project_2022_trade_game.databinding.FragmentResourceSetupBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.logging.Logger


class ResourceSetupFragment : Fragment(R.layout.fragment_resource_setup) {

    val LOG = Logger.getLogger(this.javaClass.name)

    //val database =
    //    FirebaseDatabase.getInstance("https://tradegametoolvma2022-default-rtdb.europe-west1.firebasedatabase.app").getReference("Games")

    var _binding: FragmentResourceSetupBinding? = null
    val binding get() = _binding!!
    //var resNamesList: ArrayList<EditText> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        binding.onlyOneToXCheckbox.setOnClickListener {
            Toast.makeText(activity, "checkbox clicked", Toast.LENGTH_SHORT).show()

        }
        binding.nextButton.setOnClickListener {
            saveInitialGame()
            Navigation.findNavController(view)
                .navigate(R.id.action_resourceSetupFragment_to_phaseEditFragment)
        }
    }

    private fun saveInitialGame() {
        val resNames: List<String> = binding.resNamesTextField.text.toString().split(',')
        resNames.forEach { it.trim() }
        // convert to map
        val resNamesMap: Map<String, String> =
            resNames.associateBy { resNames.indexOf(it).toString() }

        val game = Game(
            binding.nameOfGameTextField.text.toString(),
            resNames.size,
            resNamesMap,
            Integer.parseInt(binding.maxRatioTextField.text.toString()),
            binding.onlyOneToXCheckbox.isChecked
        )

        LOG.warning("ACTUAL LOG ???  ${game.nameOfGame}")
        print("Game created and db tries to upload")
        MyManager.createNewGame(game)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}