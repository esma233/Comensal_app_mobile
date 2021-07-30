package com.caanvi.comensal_app_mobile.Login.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caanvi.comensal_app_mobile.Login.Modals.User
import com.caanvi.comensal_app_mobile.Login.SQLite.DatabaseHelper
import com.caanvi.comensal_app_mobile.Login.Storage.usuarioData
import com.caanvi.comensal_app_mobile.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    lateinit var handler: DatabaseHelper

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = DatabaseHelper(this)

        binding.logOutBtn.setOnClickListener{
            eliminarSQLite(usuarioData.idGeneral)
        }
    }


    //cambiar scena

   override fun onStart() {
       super.onStart()
       binding.nombreTxt.text = usuarioData.emailGeneral
   }

    fun eliminarSQLite(id:String){
        handler.deleteDB(id)
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}

