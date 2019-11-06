package com.app.aiicapinvest


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class Login : AppCompatActivity(), View.OnClickListener {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)

        login.setOnClickListener(this)
        sign_up.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view!!.id) {
            login.id -> {
                val email = "${email.text}"
                val password = "${password.text}"

                val payload = JSONObject()
                payload.put("email", email)
                payload.put("password", password)

                Login(this).execute(payload.toString())
            }
            sign_up.id -> {
                // navController.navigate(R.id.action_login_to_signUp)

                Intent(this@Login, SignUp::class.java).apply {
                    startActivity(this)
                }
            }
        }
    }

    internal inner class Login(var context: Context) : AsyncTask<String, Void, String>() {

        lateinit var dialog: AlertDialog

        override fun onPreExecute() {
            super.onPreExecute()

            // Save User logged_in status to Cache
            cacheUser(remember_me.isChecked)

            dialog = SpotsDialog.Builder()
                .setContext(context)
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

            /**
             *  Check that the API returns a response containing an access_token, a refresh_token
             *  and logged_in_user data.
             */
            val response = JSONObject(result!!)
            if(response.has("access_token")){
                Toast.makeText(context, "login successful", Toast.LENGTH_SHORT).show()

                save_user_data(response.get("logged_in_user").toString())
                Intent(context, Home::class.java).also {
                    startActivity(it)
                    finish()
                }
                /*
                navController.navigate(R.id.action_login_to_home, null,
                    NavOptions.Builder().setPopUpTo(R.id.login, false).build())
                 */
            }
        }
    }

    /**
     *  Snippet responsible for caching the User to ensure
     *  consequent times the User doesn't need to login after the first time
     */
    private fun cacheUser(status: Boolean) {
        /**
         *  Handling the Sessioning of the User
         */
        val file = File("${cacheDir.path}/logged_in.txt")
        val fw = FileWriter(file)
        fw.write(status.toString())
        fw.close()
    }

    private fun save_user_data(data: String){
        openFileOutput("user_data.txt", Context.MODE_PRIVATE).use {
            it.write(data.toByteArray())
            it.close()
        }
    }
}
