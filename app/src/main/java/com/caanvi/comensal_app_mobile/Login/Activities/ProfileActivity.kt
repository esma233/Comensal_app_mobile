package com.caanvi.comensal_app_mobile.Login.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caanvi.comensal_app_mobile.Login.Storage.UserData
import com.caanvi.comensal_app_mobile.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    //cambiar scena

   override fun onStart() {
       super.onStart()
       binding.nombreTxt.text = "Bienvenido " + UserData.emailGeneral;
   }
}

