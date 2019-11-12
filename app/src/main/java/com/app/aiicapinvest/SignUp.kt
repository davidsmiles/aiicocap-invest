package com.app.aiicapinvest


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.aiicapinvest.models.User
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.json.JSONObject
import java.util.*


class SignUp : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_sign_up)

        sign_up.setOnClickListener(this)
        login.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        when(view!!.id){
            sign_up.id -> {
                val firstname = "${firstname.text}"
                val lastname = "${lastname.text}"
                val email = "${email.text}"
                val phone = "${phone.text}"
                val password = "${password.text}"
                val confirm_password = "${confirm_password.text}"

                val payload = JSONObject()
                payload.put("firstname", firstname)
                payload.put("lastname", lastname)
                payload.put("email", email)
            //    payload.put("phone", phone)
                payload.put("password", password)

                if(Helper.isNetworkConnected(this)) Signup(this).execute(payload.toString())
                else{
                    AlertDialog.Builder(this).setTitle("No Internet Connection")
                        .setMessage("Please check your internet connection and try again")
                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                        .setIcon(android.R.drawable.ic_dialog_alert).show()
                }
            }
            login.id -> {
                Intent(this@SignUp, Login::class.java).apply {
                    startActivity(this)
                }
            }
        }
    }

    internal inner class Signup(var context: Context) : AsyncTask<String, Void, String>(){

        lateinit var dialog: AlertDialog

        override fun onPreExecute() {
            super.onPreExecute()

            dialog = SpotsDialog.Builder()
                .setContext(context)
                .setMessage(String.format(Locale.getDefault(), "Processing..."))
                .setCancelable(false)
                .setTheme(R.style.Custom)
                .build()
        }

        override fun doInBackground(vararg payload: String): String? {

            val signup = API.signup()

            publishProgress()

            return API.makeApiCall(signup, json = payload[0])
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)

            dialog.show()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            dialog.dismiss()


            val response = JSONObject(result!!)
            if(response.has("email")) {
                Toast.makeText(context, response.getString("email"), Toast.LENGTH_SHORT).show()

                User.save_user_data(context, result)
                Intent(context, Home::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
        }
    }

}
