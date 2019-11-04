package com.app.aiicapinvest

import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import org.json.JSONObject
import org.json.JSONException




object API {

    private const val URL = "https://aiicocap-restapi.herokuapp.com"

    fun signup() = "${URL}/"

    fun signin(user_id: String, password: String) = "${URL}action=signin&user_id=$user_id&password=$password"

    fun makeApiCall(url: URL, jsonToString: String = ""): String? {
        var jsonResponse: String? = null
        var urlConnection: HttpURLConnection? = null
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.apply {
                requestMethod = "GET"
            //    setRequestProperty("Content-Type", "application/json; utf-8")
            //    setRequestProperty("Accept", "application/json")
                connectTimeout = 10000
                doOutput = true
                connect()
            }

            outputStream = urlConnection.outputStream
            inputStream = urlConnection.inputStream

        //    writeToStream(outputStream, jsonToString)
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

    private fun writeToStream(outputStream: OutputStream?, jsonToString: String){
        val input = jsonToString.toByteArray(Charset.forName("utf-8"))
        outputStream!!.write(input, 0, input.size)
    }

    @Throws(IOException::class)
    private fun readFromStream(inputStream: InputStream?): String {

        val inputStreamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
        val reader = BufferedReader(inputStreamReader)

        return reader.readText()
    }

    @Throws(IOException::class, JSONException::class)
    fun uploadToServer(): JSONObject {
        val query = "https://aiicocap-restapi.herokuapp.com/signup"
        val json = "{\"firstname\":david}"

        val url = URL(query)
        val conn = url.openConnection() as HttpURLConnection
        conn.connectTimeout = 5000
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
        conn.doOutput = true
        conn.doInput = true
        conn.requestMethod = "POST"

        val os = conn.outputStream
        os.write(json.toByteArray(charset("UTF-8")))
        os.close()

        // read the response
        val ins = BufferedInputStream(conn.inputStream)
        val result = readFromStream(ins)
        val jsonObject = JSONObject(result)


        ins.close()
        conn.disconnect()

        return jsonObject
    }

}