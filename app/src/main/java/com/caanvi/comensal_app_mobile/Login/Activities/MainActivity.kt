package com.caanvi.comensal_app_mobile.Login.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.caanvi.comensal_app_mobile.Login.Api.RetrofitClient
import com.caanvi.comensal_app_mobile.Login.Modals.loginResponse
import com.caanvi.comensal_app_mobile.Login.Modals.restorePassword
import com.caanvi.comensal_app_mobile.Login.SQLite.DatabaseHelper
import com.caanvi.comensal_app_mobile.Login.Storage.usuarioData
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.ActivityMainBinding
import com.caanvi.comensal_app_mobile.databinding.ActivityProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var handler: DatabaseHelper

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = DatabaseHelper(this)

        buscarSQLite()


        binding.buttonLogin.setOnClickListener {
            var email: String = binding.emailTxt.text.toString()
            var password:String = binding.passwordTxt.text.toString()

            login(email.trim(), password.trim())
        }

        binding.buttonForgot.setOnClickListener {
            val intent = Intent(applicationContext, forgotPassword::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


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

                        //SQLite lo recordamos
                        insertarSQLite(usuarioData.idGeneral ,usuarioData.emailGeneral)

                        //Mensaje de Inicio de Session Correcto
                        Toast.makeText(applicationContext, "Log In Correcto", Toast.LENGTH_LONG).show()

                        //Cambio de Pantalla
                        val intent = Intent(applicationContext, ProfileActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    }else{
                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                    }

                }
            })
    }



    //Funciones para SQLite
    fun buscarSQLite(){
        if (handler.selectDB()){
            Toast.makeText(applicationContext,  "Usuario Recordado", Toast.LENGTH_LONG).show()

            val intent = Intent(applicationContext, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }else{
            //Toast.makeText(applicationContext, "No recuerdo a ningun usuario", Toast.LENGTH_LONG).show()
        }
    }

    fun insertarSQLite(id:String, email: String){
        handler.insertDB(id, email)
    }
}