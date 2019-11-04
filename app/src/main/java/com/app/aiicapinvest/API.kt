package com.app.aiicapinvest

import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

object API {

    const val URL = "https://aiicocap-restapi.herokuapp.com"

    fun signup() = "${URL}/signup"

    fun login() = "${URL}/login"

    fun makeApiCall(url: URL, method: String = "POST", json: String = ""): String? {
        var jsonResponse: String? = null
        var urlConnection: HttpURLConnection? = null
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.apply {
                requestMethod = method
                connectTimeout = 10000

                setRequestProperty("Accept", "application/json")
                setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                doInput = true
                doOutput = true

                connect()
            }

            if(method.contentEquals("POST"))
                outputStream = urlConnection.outputStream
                outputStream!!.write(json.toByteArray(), 0, json.length)

            inputStream = urlConnection.inputStream

            jsonResponse = readFromStream(inputStream)

        } catch (e: IOException) { }
        finally {
            urlConnection?.disconnect()
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

        return jsonResponse
    }

    @Throws(IOException::class)
    private fun readFromStream(inputStream: InputStream?): String {

        val inputStreamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
        val reader = BufferedReader(inputStreamReader)

        return reader.readText()
    }

}