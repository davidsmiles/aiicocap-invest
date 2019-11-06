package com.app.aiicapinvest


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.util.*


class Login : AppCompatActivity(), View.OnClickListener {

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

                if(Helper.isNetworkConnected(this)) Login(this).execute(payload.toString())
                else{
                    AlertDialog.Builder(this).setTitle("No Internet Connection")
                        .setMessage("Please check your internet connection and try again")
                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                        .setIcon(android.R.drawable.ic_dialog_alert).show()
                }
            }
            sign_up.id -> {
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
                toast("login successful")

                save_user_data(response.get("logged_in_user").toString())
                Intent(context, Home::class.java).also {
                    startActivity(it)
                    finish()
                }
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
