package com.dartmedia.apptrack

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import com.dartmedia.apptrack.remote.SessionManager
import com.dartmedia.apptrack.remote.TrackingApiConfig
import com.dartmedia.apptrack.remote.responses.AddLogTrackModel
import com.dartmedia.apptrack.remote.responses.AddLogTrackResponseModel
import com.dartmedia.apptrack.remote.responses.LoginRequestModel
import com.dartmedia.apptrack.remote.responses.LoginResponseModel
import com.dartmedia.apptrack.utills.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionReport(context: Context) {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sessionManager: SessionManager

    var mContext = context

    fun startLogin(email: String, password: String) {
        val client = TrackingApiConfig.getApiService(mContext).login(
            LoginRequestModel(
                email,
                password
            )
        )
        client?.enqueue(object : Callback<LoginResponseModel?> {
            override fun onResponse(
                call: Call<LoginResponseModel?>,
                response: Response<LoginResponseModel?>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    if (responseBody != null) {
                        sessionManager.saveAuthToken(responseBody.data.accessToken)
                        //TODO : Set data to send back to Base
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponseModel?>, t: Throwable) {
                Toast.makeText(mContext, "Connection Failed", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    fun addLogTrack(nameAction: String, action: String) {
        val client = TrackingApiConfig.getApiService(mContext).addLogTrack(
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
                        Toast.makeText(mContext, responseBody.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AddLogTrackResponseModel?>, t: Throwable) {
                Toast.makeText(mContext, "Connection Failed", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

//    private fun setSession(session: LoginResponseModel) {
//        val editor: SharedPreferences.Editor = sharedPreferences.edit()
//        editor.putString(Const.KEY_TOKEN, session.data.accessToken)
//        editor.apply()
//    }
}