package com.example.vma_project_2022_trade_game

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.R.id.nav_host_fragment_container
import com.google.android.material.R.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {



        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.database.setPersistenceEnabled(true)
        setContentView(R.layout.activity_main)


        //navController.navigate(R.id.)
        //set fragment main menu
        //supportFragmentManager.beginTransaction().add(id.container,MainMenuFragment()).commit()
    }
}