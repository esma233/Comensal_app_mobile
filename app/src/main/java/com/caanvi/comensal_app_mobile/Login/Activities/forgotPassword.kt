package com.caanvi.comensal_app_mobile.Login.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.caanvi.comensal_app_mobile.Login.Api.RetrofitClient
import com.caanvi.comensal_app_mobile.Login.Modals.restorePassword
import com.caanvi.comensal_app_mobile.Login.SQLite.DatabaseHelper
import com.caanvi.comensal_app_mobile.databinding.ActivityForgotPasswordBinding
import com.caanvi.comensal_app_mobile.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class forgotPassword : AppCompatActivity() {

    lateinit var handler: DatabaseHelper

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.restoreBtn.setOnClickListener{
            var email:String = binding.emailRestoreTxt.text.toString()
            restorePassword(email)
        }
    }

    fun restorePassword(email: String){

        RetrofitClient.instance.restorePassword(email)
            .enqueue(object: Callback<restorePassword> {

                override fun onFailure(call: Call<restorePassword>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<restorePassword>, response: Response<restorePassword>) {
                    if (response.body()?.conecto!!) {

                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG)
                            .show()

                    } else {
                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
    }

}