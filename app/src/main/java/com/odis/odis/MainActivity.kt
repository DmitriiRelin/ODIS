package com.odis.odis

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.odis.odis.Utils.SCROLL_TO_X
import com.odis.odis.Utils.SCROLL_TO_Y
import com.odis.odis.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SettableToolbarTitle, ScrollableToTop {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navigationView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        setSupportActionBar(binding.mainToolbar)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_overview,
                R.id.navigation_favorites
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    override fun scrollToTop() {
        binding.mainNestedScroll.scrollTo(SCROLL_TO_X, SCROLL_TO_Y)
    }

    override fun setToolbarTitle(text: String) {
        binding.mainCollapsingToolbar.title = text
    }

}