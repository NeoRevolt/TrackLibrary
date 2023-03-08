package com.dartmedia.tracklibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dartmedia.apptrack.TransactionReport
import com.dartmedia.apptrack.remote.AuthInterceptor
import com.dartmedia.apptrack.remote.SessionManager
import com.dartmedia.tracklibrary.databinding.ActivityResultBinding

class Result : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private lateinit var sessionManager: SessionManager
    private lateinit var authInterceptor: AuthInterceptor
    private lateinit var transactionReport: TransactionReport

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactionReport = TransactionReport(this)
        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken()
        authInterceptor = AuthInterceptor(this)
        transactionReport.isActionValid("application", "open")


        binding.apply {
            if (token != null) {
                bearerTxt.text = sessionManager.fetchAuthToken()
            } else {
                bearerTxt.text = ""
            }

            transactionBtn.setOnClickListener {
                val status = transactionReport.isActionValid("ssjeas", "sade").toString()
                setTxt("ssjeas", "sade", transactionReport.getActValidationStatus())
            }

            transactionAntarRekBtn.setOnClickListener {
                val status =
                    transactionReport.isActionValid("antar rekening", "transaction").toString()
                setTxt("antar rekening", "transaction", transactionReport.getActValidationStatus())
            }

            tagihanBtn.setOnClickListener {
                val status =
                    transactionReport.isActionValid("tagihan internet", "transaction").toString()
                setTxt(
                    "tagihan internet",
                    "transaction",
                    transactionReport.getActValidationStatus()
                )
            }

            mutasiBtn.setOnClickListener {
                val status = transactionReport.isActionValid("mutasi rekening", "click").toString()
                setTxt("mutasi rekening", "click", transactionReport.getActValidationStatus())

            }

            virtualAccBtn.setOnClickListener {
                val status = transactionReport.isActionValid("virtual account", "click").toString()
                setTxt("virtual account", "click", transactionReport.getActValidationStatus())
            }

            checkSaldoClickBtn.setOnClickListener {
                val status = transactionReport.isActionValid("check saldo", "click").toString()
                setTxt("check saldo", "click", transactionReport.getActValidationStatus())
            }

            clickNotif.setOnClickListener {
                val status = transactionReport.isActionValid("notification", "click").toString()
                setTxt("notification", "click", transactionReport.getActValidationStatus())
            }
        }
    }

    private fun setTxt(nameAction: String, action: String, status: Boolean) {
        binding.apply {
            nameActionTxt.text = "Action Name : $nameAction"
            actionTxt.text = "Action : $action"
            valResultTxt.text = "Is Action Valid ? : $status"
        }
    }
}