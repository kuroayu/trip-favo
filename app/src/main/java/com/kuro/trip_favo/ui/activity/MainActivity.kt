package com.kuro.trip_favo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kuro.trip_favo.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topToolbar = findViewById<Toolbar>(R.id.top_toolbar)
        setSupportActionBar(topToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //ビューのIDとそれに含まれるアクティビティを指定してNavControllerを検索
        val navController = findNavController(R.id.nav_host_fragment)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        setupWithNavController(bottomNavigation, navController)


    }
    }



