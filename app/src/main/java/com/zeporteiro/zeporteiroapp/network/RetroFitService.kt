package com.zeporteiro.zeporteiroapp.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitService {
    val BASE_URL = "http://localhost:8080"

    fun getApi(token: String): ApiZePorteiro {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(InterceptorTokenJWT(token))
            .build()

        val client = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiZePorteiro::class.java)

        return client
    }

    class InterceptorTokenJWT(val token:String): Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val currentRequest = chain.request().newBuilder()

            currentRequest.addHeader("Authorization", "Bearer $token")

            val newRequest = currentRequest.build()
            return chain.proceed(newRequest)
        }
    }
}