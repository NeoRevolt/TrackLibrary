package com.dartmedia.tracklibrary

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dartmedia.apptrack.TransactionReport
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

    private lateinit var sessionManager: SessionManager
    private lateinit var authInterceptor: AuthInterceptor

    private lateinit var transactionReport: TransactionReport

    override fun onStart() {
        transactionReport.getAllAction()
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        transactionReport = TransactionReport(this)
        sessionManager = SessionManager(this)

        val token = sessionManager.fetchAuthToken()
        authInterceptor = AuthInterceptor(this)

        binding.apply {
            if (token != null) {
                bearerTxt.text = sessionManager.fetchAuthToken()
            } else {
                bearerTxt.text = "NONE"
            }

            transactionBtn.setOnClickListener {
                transactionReport.validateAction("ssjeas", "sade")
                setTxt("ssjeas", "sade", transactionReport.getActValidationStatus())
            }

            appOpenActionBtn.setOnClickListener {
                transactionReport.validateAction("aplication","open")
                setTxt("aplication", "open", transactionReport.getActValidationStatus())
            }

            transfeClickBtn.setOnClickListener {
                transactionReport.validateAction("transfer", "click")
                setTxt("transfer", "click", transactionReport.getActValidationStatus())

            }

            transferTransactionBtn.setOnClickListener {
                transactionReport.validateAction("transfer", "transaction")
                setTxt("transfer", "transaction", transactionReport.getActValidationStatus())

            }

            checkSaldoClickBtn.setOnClickListener {
                transactionReport.validateAction("check saldo", "click")
                setTxt("check saldo", "click", transactionReport.getActValidationStatus())

            }
        }
    }

    private fun setTxt(nameAction: String, action: String, status: String){
        binding.apply {
            nameActionTxt.text = nameAction
            actionTxt.text = action
            valResultTxt.text = status
        }
    }
}