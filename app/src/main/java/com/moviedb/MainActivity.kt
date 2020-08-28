package com.moviedb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.myNavHostFragment)
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavView.setupWithNavController(navController)

        bottomNavView.setOnNavigationItemReselectedListener { selected ->
            bottomNavView.menu.forEach { item ->
                if (item.itemId != selected.itemId) {
                    item.setEnabled(true)
                }
            }
        }
    }
}
