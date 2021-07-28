package com.caanvi.comensal_app_mobile.Login.Api

import com.caanvi.comensal_app_mobile.Login.Modals.loginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("LoginUsuario/loginUsuario.php")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String
    ): Call<loginResponse>
}