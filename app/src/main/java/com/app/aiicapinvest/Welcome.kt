package com.app.aiicapinvest


import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import java.io.File
import java.io.FileReader


class Welcome : Fragment() {


    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        splash()
    }

    private fun splash(){
        val handler = Handler()
        handler.postDelayed({
            pop()
        }, 2000)
    }

    /**
     *  Decide if User has logged in before and take them to home page
     *  if(file.exists) returns true if User has launched the App before and if true,
     *  Then move Users straight to Login page without showing them the onboarding screens.
     */
    private fun pop(){
        val file = File("${context!!.cacheDir.path}/logged_in.txt")
        if(file.exists()) {
            val fw = FileReader(file)
            when (fw.readText()) {
                "true" -> {
                    navController.navigate(R.id.action_welcome_to_home, null,
                        NavOptions.Builder().setPopUpTo(R.id.welcome, true).build())
                }
                else -> {
                    navController.navigate(R.id.action_welcome_to_login, null,
                        NavOptions.Builder().setPopUpTo(R.id.welcome, true).build())
                }
            }
        }
        else navController.navigate(R.id.action_welcome_to_onboard, null,
            NavOptions.Builder().setPopUpTo(R.id.welcome, true).build())
    }

}
