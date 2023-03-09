package com.dartmedia.tracklibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dartmedia.apptrack.TransactionReport
import com.dartmedia.apptrack.remote.ApiManager
import com.dartmedia.apptrack.remote.SessionManager
import com.dartmedia.tracklibrary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var transactionReport: TransactionReport
    private lateinit var apiManager: ApiManager

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
        apiManager = ApiManager(this)

        val token = sessionManager.fetchAuthToken()
        if (token != null) {
            val i = Intent(this, Result::class.java)
            startActivity(i)
            finish()
        }

        binding.apply {
            loginBtn.setOnClickListener {
                apiManager.setBaseUrl(ipEditText.text.toString())
                transactionReport.startLogin(EMAIL, PASS, FCM_TOKEN)
            }
        }
    }
}