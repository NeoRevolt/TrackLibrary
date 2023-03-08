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
import com.dartmedia.apptrack.utills.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * App Action-Transaction Validator Class
 *
 * @author Dzalfikri Ali Zidan
 * @version 1.0
 * @since 3 March 2023
 */
class TransactionReport(context: Context) {

    private var mContext = context
    private var listActionValidations = ArrayList<ValidationsItem>()
    private var actionValidated: Boolean = false
    private var sessionManager = SessionManager(mContext)


    /**
     * Login Request to API
     *
     * This function is experimental purpose for login in sample project,
     * please use your own Login Request !
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
                        //NOTE : SEND DATA OR INTENT TO BASE ACTIVITY (Experimental)
                        Intent().setClassName(mContext, Const.RESULT_CLASSNAME)
                            .also {
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
     * Get list of valid actions and transactions list from API,
     * and run [validateAction] when list successfully added.
     *
     * @param nameAction The purpose of the action
     * @param action Action that user do
     *
     * @return Boolean
     * */
    fun isActionValid(nameAction: String, action: String): Boolean {
        var actionValid = false
        val client = TrackingApiConfig.getApiService(mContext)
        client.getActionValidation()?.enqueue(object : Callback<ActionValidationResponse?> {
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
                        Log.d("ACTION", "ACTION ADDED")
                        validateAction(nameAction, action)
                        actionValid = true
                    }
                } else {
                    Log.d("ACTION", "ACTION NOT FOUND")
                    actionValid = false
                }
            }

            override fun onFailure(call: Call<ActionValidationResponse?>, t: Throwable) {
                Log.d("ACTION", "Connection Failed")
                actionValid = false
            }
        })
        Log.d("IsActionValid", "$actionValid")
        return actionValid
    }

    /**
     * Validate Action and Add Action to API when parameters passed to this function is equal to
     * list of valid actions and transactions from API you get from [isActionValid]
     *
     * @param nameAction The purpose of the action
     * @param action Action that user do
     *
     * @return Boolean
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
                            Log.d("ACTION", "Action Successfully added to API")
                        }
                    } else {
                        actionValidated = false
                        Log.d("ACTION", "Action failed added to API")
                    }
                }

                override fun onFailure(call: Call<AddLogTrackResponseModel?>, t: Throwable) {
                    actionValidated = false
                    Log.d("ACTION", "Connection Failed")
                }

            })
        } else {
            actionValidated = false
            Log.d("ACTION", "Invalid Actions")
        }
        return actionValidated
    }

    fun getActValidationStatus(): String {
        return actionValidated.toString()
    }

}