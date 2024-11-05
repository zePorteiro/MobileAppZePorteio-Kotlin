package com.zeporteiro.zeporteiroapp.network

import com.zeporteiro.zeporteiroapp.data.Entrega
import com.zeporteiro.zeporteiroapp.data.LoginRequest
import com.zeporteiro.zeporteiroapp.data.LoginResponse
import com.zeporteiro.zeporteiroapp.data.Morador
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiZePorteiro {
    @POST("/moradores/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("/moradores")
    suspend fun getMoradores(): Response<List<Morador>>

    @PUT("/moradores/{id}")
    suspend fun putMorador(@Path("id") id: Int, @Body morador: Morador): Response<Morador>

    @POST("/moradores/cadastrarMorador")
    suspend fun postMorador(@Body morador: Morador): Response<Morador>

    @DELETE("/moradores/{id}")
    suspend fun deleteMorador(@Path("id") id: Int): Response<Unit>

    @GET("/entregas/{id}")
    suspend fun getEntregas(@Path("id") id: Int): Response<List<Entrega>>

    @PUT("/entregas/{id}")
    suspend fun putEntrega(@Path("id") id: Int, @Body entrega: Entrega): Response<Entrega>
}