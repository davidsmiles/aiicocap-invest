package com.app.aiicapinvest.models

import android.content.Context

object User {
    fun save_user_data(context: Context, data: String){
        context.openFileOutput("user_data.txt", Context.MODE_PRIVATE).use {
            it.write(data.toByteArray())
            it.close()
        }
    }
}