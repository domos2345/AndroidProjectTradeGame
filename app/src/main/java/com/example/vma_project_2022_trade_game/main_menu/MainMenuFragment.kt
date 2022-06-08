package com.example.vma_project_2022_trade_game.main_menu

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vma_project_2022_trade_game.data.Game
import com.example.vma_project_2022_trade_game.MyManager
import com.example.vma_project_2022_trade_game.R
import com.example.vma_project_2022_trade_game.data.Constants
import com.example.vma_project_2022_trade_game.databinding.FragmentMainMenuBinding
import java.util.logging.Logger

class MainMenuFragment : Fragment(R.layout.fragment_main_menu), RecyclerViewCallback {

    val LOG = Logger.getLogger(this.javaClass.name)
    private val viewModel by viewModels<MainMenuViewModel>()
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!
    lateinit var games: MutableList<Game>
    lateinit var adapter: MainMenuAdapter

    lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    //val phase: String get() = sp.getInt("phase", 0).toString()


    lateinit var listOfGridItems: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sp = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        editor = sp.edit()
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        //val view = inflater.inflate(R.layout.fragment_main_menu, container, false)
        //val addGameButton: FloatingActionButton = view.findViewById(R.id.addGameButton)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.games.observe(requireActivity()) { games ->
            val recyclerView = binding.mainMenuRecyclerView
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            adapter = MainMenuAdapter(games!!, this)
            recyclerView.adapter = adapter

            LOG.info(games.toString())
        }



        binding.addGameButton.setOnClickListener {
            MyManager.gameActual = MyManager.genericGame
            Navigation.findNavController(view)
                .navigate(R.id.action_mainMenuFragment_to_resourceSetupFragment)
            //val fragment = ResourceSetupFragment()
            //val transaction = fragmentManager?.beginTransaction()
            // transaction?.replace(R.id.nav_container,fragment)?.commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun playGame(game: Game) {

        MyManager.gameActual = game
        editor.putBoolean("edit", false)
        editor.putInt("phase", 1)
        val bundle = Bundle()
        bundle.putString(
            "data",
            Constants.getResValuesAsString(Constants.generateResValues() as MutableMap<Int, Int>)
        )
        Navigation.findNavController(binding.addGameButton)
            .navigate(R.id.action_mainMenuFragment_to_playingGameFragment, bundle)
    }

    override fun editGame(game: Game) {
        MyManager.gameActual = game
        editor.putBoolean("edit", true)

        Navigation.findNavController(binding.addGameButton)
            .navigate(R.id.action_mainMenuFragment_to_resourceSetupFragment)
    }


}