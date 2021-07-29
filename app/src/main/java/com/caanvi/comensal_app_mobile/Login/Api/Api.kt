package com.caanvi.comensal_app_mobile.Login.Api

import com.caanvi.comensal_app_mobile.Login.Modals.loginResponse
import com.caanvi.comensal_app_mobile.Login.Modals.restorePassword
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("LoginUsuario/loginUsuario.php")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String
    ): Call<loginResponse>


    @FormUrlEncoded
    @POST("LoginUsuario/olvideContrasena.php")
    fun restorePassword(
        @Field ("email") email:String
    ): Call<restorePassword>

}

