package com.app.aiicapinvest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.NavigationUI
import androidx.navigation.Navigation
import androidx.core.view.GravityCompat

class Home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar

    lateinit var drawerLayout: DrawerLayout

    lateinit var navController: NavController

    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupNavigation()
    }

    private fun setupNavigation() {

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        drawerLayout = findViewById(R.id.drawer_layout)

        navigationView = findViewById(R.id.navigationView)

        navController = Navigation.findNavController(this, R.id.nav_home_fragment)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        NavigationUI.setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_home_fragment), drawerLayout);
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        drawerLayout.closeDrawers()


        when(menuItem.itemId) {
            R.id.update_profile -> navController.navigate(R.id.profileFragment)
            R.id.update_kyc -> navController.navigate(R.id.KYCFragment)
            R.id.home -> navController.navigate(R.id.homeFragment)
            R.id.wallet -> navController.navigate(R.id.walletFragment)
            R.id.invest -> navController.navigate(R.id.investFragment)
            R.id.withdrawals -> navController.navigate(R.id.withdrawalsFragment)
            R.id.logout -> Toast.makeText(this, "not yet implemented", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onBackPressed() {
        when {
            drawerLayout.isDrawerOpen(GravityCompat.START) -> drawerLayout.closeDrawer(GravityCompat.START)
            else -> {
                super.onBackPressed()
            }
        }
    }
}
