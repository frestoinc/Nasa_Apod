package com.example.android.nasa_apod.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.example.android.nasa_apod.R
import com.example.android.nasa_apod.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            setSupportActionBar(it.appBarMain.toolbar)
        }

        supportFragmentManager.findFragmentById(R.id.nav_host_content).also {
            navController = (it as NavHostFragment).navController
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.nav_detail) {
                    binding.appBarMain.toolbar.title = ""
                }
            }
        }

        AppBarConfiguration.Builder(setOf(R.id.nav_main)).build().also {
            setupActionBarWithNavController(this, navController, it)
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()
}