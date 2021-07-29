package com.caanvi.comensal_app_mobile.Login.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.caanvi.comensal_app_mobile.Login.Api.RetrofitClient
import com.caanvi.comensal_app_mobile.Login.Modals.loginResponse
import com.caanvi.comensal_app_mobile.Login.Modals.restorePassword
import com.caanvi.comensal_app_mobile.Login.SQLite.DatabaseHelper
import com.caanvi.comensal_app_mobile.Login.Storage.usuarioData
import com.caanvi.comensal_app_mobile.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var handler: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler = DatabaseHelper(this)

        var email:String = "esma@gmail.com"
        var password:String = "123"

        //login(email.trim(), password.trim())

        //restorePassword(email)

        //insertarSQLite("1" ,email, password)
        buscarSQLite()
        //eliminarSQLite("1")
    }

    fun login(email: String, password:String){

        RetrofitClient.instance.userLogin(email, password)
            .enqueue(object: Callback<loginResponse> {

                override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<loginResponse>, response: Response<loginResponse>) {
                    if(response.body()?.conecto!!){

                        usuarioData.idGeneral = response.body()?.user?.id!!
                        usuarioData.emailGeneral = response.body()?.user?.email!!

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



    fun restorePassword(email: String){

        RetrofitClient.instance.restorePassword(email)
            .enqueue(object: Callback<restorePassword> {

                override fun onFailure(call: Call<restorePassword>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<restorePassword>, response: Response<restorePassword>) {
                    if (response.body()?.conecto!!) {

                        //UserData.idGeneral = response.body()?.user?.id!!
                        //UserData.emailGeneral = response.body()?.user?.email!!

                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG)
                            .show()

                    } else {
                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
    }

    fun insertarSQLite(id:String, email: String, password:String){
        handler.insertDB(id, email, password)
        Toast.makeText(applicationContext, "Datos ingresados en SQLITE", Toast.LENGTH_LONG).show()
    }

    fun buscarSQLite(){
        if (handler.selectDB()){
            val intent = Intent(applicationContext, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            Toast.makeText(applicationContext,  "Login Succesful", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(applicationContext, "Imposible no encontramos nada", Toast.LENGTH_LONG).show()
        }
    }

    fun eliminarSQLite(id:String){
        handler.deleteDB(id)
    }
}