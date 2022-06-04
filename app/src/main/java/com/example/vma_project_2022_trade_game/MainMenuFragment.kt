package com.example.vma_project_2022_trade_game

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.vma_project_2022_trade_game.databinding.FragmentMainMenuBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainMenuFragment : Fragment(R.layout.fragment_main_menu) {

    private lateinit var viewModel: MainMenuViewModel
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        //val view = inflater.inflate(R.layout.fragment_main_menu, container, false)
        //val addGameButton: FloatingActionButton = view.findViewById(R.id.addGameButton)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addGameButton.setOnClickListener {
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


}