package com.app.aiicapinvest


import android.app.AlertDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.header_layout.*
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class SignUp : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        sign_up.setOnClickListener(this)
        login.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            sign_up.id -> {
                val firstname = "${firstname.text}"
                val lastname = "${lastname.text}"
                val email = "${email_address.text}"
                val phone = "${phone.text}"
                val password = "${confirm_password.text}"

                val payload = JSONObject()
                payload.put("firstname", firstname)
                payload.put("lastname", lastname)
                payload.put("email", email)
                payload.put("password", password)

                Signup(context!!).execute(payload.toString())
            }
            login.id -> navController.navigate(R.id.action_signUp_to_login)
        }
    }

    internal inner class Signup(var ctx: Context) : AsyncTask<String, Void, String>() {

        lateinit var dialog: AlertDialog

        override fun onPreExecute() {
            super.onPreExecute()

            dialog = SpotsDialog.Builder()
                .setContext(ctx)
                .setMessage(String.format(Locale.getDefault(), "Processing..."))
                .setCancelable(false)
                .setTheme(R.style.Custom)
                .build()
        }

        override fun doInBackground(vararg payload: String): String? {

            val signup = API.signup()

            var url: URL? = null

            try {
                url = URL(signup)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }

            publishProgress()

            return API.makeApiCall(url!!, json = payload[0])
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)

            dialog.show()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            dialog.dismiss()

            Toast.makeText(ctx, result.toString(), Toast.LENGTH_SHORT).show()

//            if (result != null) {
//                if(result.contains("true")){
//                    val welcome = WelcomeSheet()
//                    welcome.show(supportFragmentManager, "example")
//                }
//            }
        }
    }
}
