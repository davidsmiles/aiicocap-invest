package com.app.aiicapinvest

import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

object API {

    const val URL = "https://aiicocap-restapi.herokuapp.com"

    fun signup() = "${URL}/signup"

    fun login(user_id: String, password: String) = "${URL}/login"

    fun makeApiCall(url: URL): String? {
        var jsonResponse: String? = null
        var urlConnection: HttpURLConnection? = null
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.apply {
                requestMethod = "POST"
                connectTimeout = 10000
              //  addRequestProperty("Content-Type", "application/json")
              //  setRequestProperty("Content-Type", "application/json");

                setRequestProperty("Accept", "application/json")
                doInput = true
                doOutput = true
                setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connect()
            }
            val out = "{" +
                    "\"firstname\":\"davido\"," +
                    "\"lastname\":\"ugbero\"," +
                    "\"email\":\"ugberodavid@gmail.com\"," +
                    "\"password\":\"ugbero\"" +
                    "}"
            outputStream = urlConnection.outputStream
            outputStream.write(out.toByteArray(), 0, out.length)

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