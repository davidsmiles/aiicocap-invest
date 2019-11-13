package com.app.aiicapinvest


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadUserInfo()
    }

    private fun loadUserInfo(){
        var data: String? = null
        context!!.openFileInput("user_data.txt").use{
            val ins = InputStreamReader(it)
            val bis = BufferedReader(ins)
            data = bis.readText()
        }

        JSONObject(data!!).also {
            val firstname = it.getString("firstname")
            val lastname = it.getString("lastname")
            val email = it.getString("email")

            fn.setText(firstname)
            ln.setText(lastname)
            em.setText(email)
        }
    }
}
