package com.zeporteiro.zeporteiroapp.network

import com.zeporteiro.zeporteiroapp.data.CadastroRequest
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
import retrofit2.http.Query

interface ApiZePorteiro {
    @POST("moradores/cadastrarMorador")
    suspend fun cadastrarMorador(@Body request: CadastroRequest): Response<Morador>

    @GET("apartamentos/verificar")
    suspend fun verificarApartamento(
        @Query("cep") cep: String,
        @Query("numero") numeroApartamento: String
    ): Response<Unit>

    @POST("/moradores/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("/moradores")
    suspend fun getMoradores(): Response<List<Morador>>

    @PUT("/moradores/{id}")
    suspend fun putMorador(@Path("id") id: Int, @Body morador: Morador): Response<Morador>

    @DELETE("/moradores/{id}")
    suspend fun deleteMorador(@Path("id") id: Int): Response<Unit>

    @GET("moradores/{id}")
    suspend fun getMoradorById(@Path("id") id: Int): Response<Morador>

    @GET("entregas/apartamento/{numeroApartamento}")
    suspend fun getEntregasPorApartamento(
        @Path("numeroApartamento") numeroApartamento: String
    ): Response<List<Entrega>>

    @PUT("entregas/{entregaId}/recebida")
    suspend fun registrarEntregaRecebida(
        @Path("entregaId") entregaId: Int
    ): Response<Entrega>
}