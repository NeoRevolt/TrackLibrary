package com.dartmedia.tracklibrary

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dartmedia.apptrack.remote.AuthInterceptor
import com.dartmedia.apptrack.remote.SessionManager
import com.dartmedia.apptrack.remote.TrackingApiConfig
import com.dartmedia.apptrack.remote.responses.AddLogTrackModel
import com.dartmedia.apptrack.remote.responses.AddLogTrackResponseModel
import com.dartmedia.tracklibrary.databinding.ActivityResultBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.channels.ClosedByInterruptException

class Result : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sessionManager: SessionManager
    private lateinit var authInterceptor: AuthInterceptor

    private var SHARED_PREF_NAME = "mypref"
    private var KEY_TOKEN = "key_token"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sessionManager = SessionManager(this)
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        val token = sharedPreferences.getString(KEY_TOKEN, 0.toString())
        authInterceptor = AuthInterceptor(this)

        binding.apply {
            if (token != null) {
                bearerTxt.text = sessionManager.fetchAuthToken()
            }
        }

        binding.transactionBtn.setOnClickListener {
            addLogTrack("transfer", "transaction")
        }
    }

    private fun addLogTrack(nameAction: String, action: String) {
        val token = sessionManager.fetchAuthToken()
        if (token != null) {
        }
        val client = TrackingApiConfig.getApiService(this).addLogTrack(
            AddLogTrackModel(
                nameAction,
                action
            )
        )
        client?.enqueue(object : Callback<AddLogTrackResponseModel?> {
            override fun onResponse(
                call: Call<AddLogTrackResponseModel?>,
                response: Response<AddLogTrackResponseModel?>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    if (responseBody != null) {
                        Toast.makeText(this@Result, response.message(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@Result, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AddLogTrackResponseModel?>, t: Throwable) {
                Toast.makeText(this@Result, "Connection Failed", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }
}