package com.dartmedia.tracklibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dartmedia.apptrack.TransactionReport
import com.dartmedia.apptrack.remote.SessionManager
import com.dartmedia.tracklibrary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var transactionReport: TransactionReport

    companion object {
        const val EMAIL = "admindartmedia@gmail.com"
        const val PASS = "Admindartmedia123"
        const val FCM_TOKEN = "1231sadadasd31234"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        transactionReport = TransactionReport(this)

        val token = sessionManager.fetchAuthToken()
        if (token != null) {
            val i = Intent(this, Result::class.java)
            startActivity(i)
            finish()
        }

        binding.apply {
            loginBtn.setOnClickListener {
                transactionReport.startLogin(EMAIL, PASS, FCM_TOKEN)
            }
        }
    }

//    private fun startLogin() {
//        val client = TrackingApiConfig.getApiService(this).login(
//            LoginRequestModel(
//                email = "admindartmedia@gmail.com",
//                password = "Admindartmedia123",
//                fcmToken = "1231sadadasd31234"
//            )
//        )
//        client?.enqueue(object : Callback<LoginResponseModel?>{
//            override fun onResponse(call: Call<LoginResponseModel?>, response: Response<LoginResponseModel?>) {
//                val responseBody = response.body()
//                if (response.isSuccessful){
//                    if (responseBody != null){
//                        Toast.makeText(this@MainActivity, responseBody.message, Toast.LENGTH_SHORT).show()
//                        sessionManager.saveAuthToken(responseBody.data.accessToken)
//                        Intent(this@MainActivity, Result::class.java).also {
//                            startActivity(it)
//                            finish()
//                        }
//                    }
//                }
//                else {
//                    Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<LoginResponseModel?>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Connection Failed", Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//        })
//    }
}