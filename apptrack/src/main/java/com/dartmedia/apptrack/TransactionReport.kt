package com.dartmedia.apptrack

import android.content.Context
import android.content.Intent
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

/**
 * App Action-Transaction Validator Class
 *
 *
 * @author Dzalfikri Ali Zidan
 * @version 1.0
 * @since 3 March 2023
 */

class TransactionReport(context: Context) {

    companion object {
        private const val modulePackageName = "com.dartmedia.tracklibrary"
        private const val resultClassName = "$modulePackageName.Result"
    }

    private var mContext = context
    private var listActionValidations = ArrayList<ValidationsItem>()
    private var actionValidated: Boolean = false
    private var sessionManager = SessionManager(mContext)


    /**
     * Login Request to API
     *
     * */
    fun startLogin(email: String, password: String, fcmToken: String) {
        val client = TrackingApiConfig.getApiService(mContext).login(
            LoginRequestModel(
                password,
                email,
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
                        Toast.makeText(mContext, responseBody.message, Toast.LENGTH_SHORT).show()
                        sessionManager.saveAuthToken(responseBody.data.accessToken)
                        //TODO : SEND DATA OR INTENT TO BASE ACTIVITY
                        Intent().setClassName(mContext, resultClassName)
                            .also {
                                // Send Image URL to DFM
                                mContext.startActivity(it)
                            }

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


    /**
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

    /**
     * Validate Action and Add Action to API
     * */
    fun validateAction(nameAction: String, action: String): Boolean {
        actionValidated = false
        Log.d("LIST", "List Validation : $listActionValidations")

        for (i in listActionValidations.indices) {
            if (nameAction == listActionValidations[i].nameAction && action == listActionValidations[i].action) {
                actionValidated = true
                break
            }
        }
        Log.d("VALID", "Is Action Valid ? : $actionValidated")

        if (actionValidated) {
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
                            actionValidated = true
                            Toast.makeText(mContext, responseBody.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        actionValidated = true
                        Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AddLogTrackResponseModel?>, t: Throwable) {
                    actionValidated = true
                    Toast.makeText(mContext, "Connection Failed", Toast.LENGTH_SHORT)
                        .show()
                }

            })
        } else {
            actionValidated = false
            Toast.makeText(mContext, "Invalid Action", Toast.LENGTH_SHORT)
                .show()
        }
        return actionValidated
    }

    fun getActValidationStatus(): String {
        return actionValidated.toString()
    }

}