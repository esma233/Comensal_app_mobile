package com.caanvi.comensal_app_mobile.Login.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.caanvi.comensal_app_mobile.Login.Modals.loginResponse
import com.caanvi.comensal_app_mobile.R
import retrofit2.Callback

import android.content.Intent
import com.caanvi.comensal_app_mobile.Login.Api.RetrofitClient
import com.caanvi.comensal_app_mobile.Login.Storage.UserData
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var email:String = "esma@gmail.com"
        var password:String = "123"

        login(email.trim(), password.trim())
    }

    fun login(email: String, password:String){

        RetrofitClient.instance.userLogin(email, password)
            .enqueue(object: Callback<loginResponse> {
                override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<loginResponse>, response: Response<loginResponse>) {
                    if(response.body()?.conecto!!){

                        UserData.idGeneral = response.body()?.user?.id!!
                        UserData.emailGeneral = response.body()?.user?.email!!

                        Toast.makeText(applicationContext, "Log In Succesful", Toast.LENGTH_LONG).show()

                        val intent = Intent(applicationContext, ProfileActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    }else{
                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                    }

                }
            })
    }
}