package com.kuro.trip_favo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kuro.trip_favo.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val toolbar = findViewById<Toolbar>(R.id.top_toolbar)

        //setupWithNavController: findNavControllerのラッパー
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.favorite, R.id.search))
        setupWithNavController(bottomNavigation, navController)
        setupWithNavController(toolbar, navController,appBarConfiguration)



    }
}



