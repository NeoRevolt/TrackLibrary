package com.dartmedia.tracklibrary

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dartmedia.apptrack.remote.SessionManager
import com.dartmedia.tracklibrary.databinding.ActivityMainBinding
import com.dartmedia.tracklibrary.databinding.ActivityResultBinding

class Result : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sessionManager: SessionManager

    private var SHARED_PREF_NAME = "mypref"
    private var KEY_TOKEN = "key_token"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        val token = sharedPreferences.getString(KEY_TOKEN, 0.toString())
        binding.apply {
            if (token != null){
                bearerTxt.text = token
            }
        }
    }
}