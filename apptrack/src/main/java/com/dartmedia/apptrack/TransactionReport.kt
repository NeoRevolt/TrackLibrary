package com.dartmedia.apptrack

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.dartmedia.apptrack.remote.SessionManager
import com.dartmedia.apptrack.remote.TrackingApiConfig
import com.dartmedia.apptrack.remote.responses.AddLogTrackModel
import com.dartmedia.apptrack.remote.responses.AddLogTrackResponseModel
import com.dartmedia.apptrack.remote.responses.LoginRequestModel
import com.dartmedia.apptrack.remote.responses.LoginResponseModel
import com.dartmedia.apptrack.remote.responses.actionvalidation.ActionValidationResponse
import com.dartmedia.apptrack.remote.responses.actionvalidation.ValidationsItem
import com.dartmedia.apptrack.remote.responses.getlogtrack.GetLogTrackResponseModel
import com.dartmedia.apptrack.remote.responses.getlogtrack.LogTrackersItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var validateState: Boolean = false

class TransactionReport(context: Context) {

    private lateinit var sessionManager: SessionManager
    var mContext = context
    var getLogTrackResponseModelList = ArrayList<LogTrackersItem>()
    var listActionValidations = ArrayList<ValidationsItem>()

    /*
    * Login Request to API
    * */
    fun startLogin(email: String, password: String, fcmToken: String) {
        val client = TrackingApiConfig.getApiService(mContext).login(
            LoginRequestModel(
                email,
                password,
                fcmToken
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


    /*
    * Send Log Actions to API
    * */
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


    /*
    * Get Log Track
    * */
    fun getLogTrack() {
        val client = TrackingApiConfig.getApiService(mContext).getLogTrack()
        client?.enqueue(object : Callback<GetLogTrackResponseModel?> {
            override fun onResponse(
                call: Call<GetLogTrackResponseModel?>,
                response: Response<GetLogTrackResponseModel?>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    if (responseBody != null) {
                        responseBody.data.logTrackers.let { getLogTrackResponseModelList.addAll(it) }
                        Toast.makeText(mContext, responseBody.status, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetLogTrackResponseModel?>, t: Throwable) {
                Toast.makeText(mContext, "Connection Failed", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


    /*
    * TODO: Make sure Validate Action RUN every time app is Started
    * */
    fun getAllAction() {
        val client = TrackingApiConfig.getApiService(mContext).getActionValidation()
        client?.enqueue(object : Callback<ActionValidationResponse?> {
            override fun onResponse(
                call: Call<ActionValidationResponse?>,
                response: Response<ActionValidationResponse?>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    if (responseBody != null) {
                        responseBody.data.validations.let {
                            listActionValidations.addAll(it)
                        }
                        Toast.makeText(mContext, responseBody.status, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ActionValidationResponse?>, t: Throwable) {
                Toast.makeText(mContext, "Connection Failed", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


    fun validateAction(nameAction: String, action: String): Boolean {
        Log.d("ValidateAction", "BOOLEAN : $listActionValidations")
        for (i in listActionValidations.indices) {
            if (nameAction == listActionValidations[i].nameAction && action == listActionValidations[i].action) {
                validateState = true
            }
        }
        if (validateState){
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
                            validateState = true
                            Toast.makeText(mContext, responseBody.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        validateState = true
                        Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AddLogTrackResponseModel?>, t: Throwable) {
                    validateState = true
                    Toast.makeText(mContext, "Connection Failed", Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }else{
            validateState = false
        }
        return validateState
    }

    fun getActValidationStatus():String{
        return validateState.toString()
    }

//    private fun setSession(session: LoginResponseModel) {
//        val editor: SharedPreferences.Editor = sharedPreferences.edit()
//        editor.putString(Const.KEY_TOKEN, session.data.accessToken)
//        editor.apply()
//    }
}