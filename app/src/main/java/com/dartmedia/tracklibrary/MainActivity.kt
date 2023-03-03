package com.dartmedia.tracklibrary

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dartmedia.apptrack.remote.SessionManager
import com.dartmedia.apptrack.remote.TrackingApiConfig
import com.dartmedia.apptrack.remote.responses.LoginResponseModel
import com.dartmedia.apptrack.remote.responses.LoginRequestModel
import com.dartmedia.tracklibrary.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        val token = sessionManager.fetchAuthToken()
        if (token != null) {
            val i = Intent(this, Result::class.java)
            startActivity(i)
            finish()
        }

        binding.apply {
            loginBtn.setOnClickListener {
                startLogin()
            }
        }
    }

    private fun startLogin() {
        val client = TrackingApiConfig.getApiService(this).login(
            LoginRequestModel(
                email = "admindartmedia@gmail.com",
                password = "Admindartmedia123",
                fcmToken = "1231sadadasd31234"
            )
        )
        client?.enqueue(object : Callback<LoginResponseModel?>{
            override fun onResponse(call: Call<LoginResponseModel?>, response: Response<LoginResponseModel?>) {
                val responseBody = response.body()
                if (response.isSuccessful){
                    if (responseBody != null){
                        Toast.makeText(this@MainActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                        sessionManager.saveAuthToken(responseBody.data.accessToken)
                        Intent(this@MainActivity, Result::class.java).also {
                            startActivity(it)
                            finish()
                        }
                    }
                }
                else {
                    Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponseModel?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Connection Failed", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }
}