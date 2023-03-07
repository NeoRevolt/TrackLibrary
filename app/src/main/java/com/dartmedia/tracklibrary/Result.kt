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
                transactionReport.isActionValid("ssjeas", "sade")
                setTxt("ssjeas", "sade", transactionReport.getActValidationStatus())
            }

            transactionAntarRekBtn.setOnClickListener {
                transactionReport.isActionValid("antar rekening", "transaction")
                setTxt("antar rekening", "transaction", transactionReport.getActValidationStatus())
            }

            tagihanBtn.setOnClickListener {
                transactionReport.isActionValid("tagihan internet", "transaction")
                setTxt("tagihan internet", "transaction", transactionReport.getActValidationStatus())
            }

            mutasiBtn.setOnClickListener {
                transactionReport.isActionValid("mutasi rekening", "click")
                setTxt("mutasi rekening", "click", transactionReport.getActValidationStatus())

            }

            virtualAccBtn.setOnClickListener {
                transactionReport.isActionValid("virtual account", "click")
                setTxt("virtual account", "click", transactionReport.getActValidationStatus())

            }

            checkSaldoClickBtn.setOnClickListener {
                transactionReport.isActionValid("check saldo", "click")
                setTxt("check saldo", "click", transactionReport.getActValidationStatus())

            }

            clickNotif.setOnClickListener {
                transactionReport.isActionValid("notification", "click")
                setTxt("notification", "click", transactionReport.getActValidationStatus())

            }
        }
    }

    private fun setTxt(nameAction: String, action: String, status: String) {
        binding.apply {
            nameActionTxt.text = "Action Name : $nameAction"
            actionTxt.text = "Action : $action"
            valResultTxt.text = "Is Action Valid ? : $status"
        }
    }
}