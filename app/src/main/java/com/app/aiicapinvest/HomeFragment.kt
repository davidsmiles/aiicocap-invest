package com.app.aiicapinvest


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
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

            greetings.text = String.format(Locale.getDefault(), "${getGreetingMessage().capitalize()} $firstname.")
        }
    }

    private fun getGreetingMessage(): String{
        val c = Calendar.getInstance()
        val timeOfDay = c.get(Calendar.HOUR_OF_DAY)

        return when (timeOfDay) {
            in 0..11 -> "Good Morning"
            in 12..15 -> "Good Afternoon"
            in 16..20 -> "Good Evening"
            in 21..23 -> "Good Night"
            else -> {
                "Hello"
            }
        }
    }

}
