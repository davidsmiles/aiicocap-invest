package com.app.aiicapinvest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.NavigationUI
import androidx.navigation.Navigation
import androidx.core.view.GravityCompat
import org.jetbrains.anko.find
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileWriter
import java.io.InputStreamReader
import java.util.*

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

        setupNavHeader()
    }

    private fun setupNavHeader(){
        val headerLayout = navigationView.inflateHeaderView(R.layout.header_layout)
        val user_name = headerLayout.find<AppCompatTextView>(R.id.user_name)
        val user_email = headerLayout.find<AppCompatTextView>(R.id.email_address)
        val user_info = loadUserInfo()
        user_name.text = user_info.first
        user_email.text = user_info.second
    }

    private fun loadUserInfo(): Pair<String, String>{
        var data: String? = null
        openFileInput("user_data.txt").use{
            val ins = InputStreamReader(it)
            val bis = BufferedReader(ins)
            data = bis.readText()
        }

        JSONObject(data!!).also {
            val firstname = it.getString("firstname")
            val lastname = it.getString("lastname")
            val email = it.getString("email")

            return Pair("$firstname $lastname", email)
        }
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
            R.id.logout -> {
                AlertDialog.Builder(this, R.style.MyDialogTheme)
                    .setMessage(String.format(Locale.getDefault(), "Are you sure you want to logout?"))
                    .setPositiveButton(String.format(Locale.getDefault(), "YES")) {_, _ ->

                        false.cacheUser()
                        deleteFile("user_data.txt")

                        Intent(this@Home, Login::class.java).apply {
                            startActivity(this)
                            finish()
                        }
                    }
                    .setNegativeButton(String.format(Locale.getDefault(), "NO")) { _, _ -> }
                    .create()
                    .show()
            }
        }
        return true
    }

    /**
     *  Snippet responsible for caching the User to ensure
     *  consequent times the User doesn't need to login after the first time
     */
    private fun Boolean.cacheUser() {
        /**
         *  Handling the Sessioning of the User
         */
        val file = File("${cacheDir.path}/logged_in.txt")
        val fw = FileWriter(file)
        fw.write(toString())
        fw.close()
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
