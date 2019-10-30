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
            navController.navigate(R.id.action_welcome_to_onboard, null)
             //   NavOptions.Builder().setPopUpTo(R.id.welcome, true).build())
        }, 2000)
    }
}
