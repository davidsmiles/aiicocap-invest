package com.app.aiicapinvest


import android.app.AlertDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL
import java.util.*


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
                val email = "${email.text}"
                val password = "${password.text}"

                val payload = JSONObject()
                payload.put("email", email)
                payload.put("password", password)

                Login(context!!).execute(payload.toString())
            }
        }
    }

    internal inner class Login(var ctx: Context) : AsyncTask<String, Void, String>() {

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

            val login = API.login()

            publishProgress()

            return API.makeApiCall(login, json = payload[0])
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)

            dialog.show()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            dialog.dismiss()

            val response = JSONObject(result!!)
            if(response.has("access_token")){
                Toast.makeText(ctx, "login successful", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_login_to_home)
            }
        }
    }
}
