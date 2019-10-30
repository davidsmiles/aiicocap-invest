package com.app.aiicapinvest


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*


class Login : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        login.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view!!.id) {
            login.id -> {
                navController.navigate(
                    R.id.action_login_to_home, null//,
                //    NavOptions.Builder().setPopUpTo(R.id.homeFragment, true).setLaunchSingleTop(true).build()
                )
            }
        }
    }
}
