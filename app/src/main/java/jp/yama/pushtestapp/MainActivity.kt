package jp.yama.pushtestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var savedToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            savedToken = savedInstanceState.getString("token")
        }
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if (!it.isSuccessful) {
                Log.w("FIREBASE", "getInstanceId failed", it.exception);
                return@addOnCompleteListener
            }
            Log.i("FIREBASE", "Token: ${it.result?.token}")
            if (savedToken != it.result?.token) {
                savedToken = it.result?.token
                val url = "https://us-central1-pushtestapp-51f6f.cloudfunctions.net/putToken?token=" + it.result?.token
                val httpGet = HttpGet()
                httpGet.doGet(url)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("token", savedToken)
    }

}

class HttpGet(): Callback {

    fun doGet(url: String) {
        val client = OkHttpClient()
        val req = Request.Builder().url(url).build()
        client.newCall(req).enqueue(this)
    }

    override fun onFailure(call: Call, e: IOException) {
        Log.w("COMMU", "HttpGet failed!", e)
    }

    override fun onResponse(call: Call, response: Response) {
        Log.i("COMMU", "HttpGet Success! status:" + response.code)
    }

}
