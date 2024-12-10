package com.zeporteiro.zeporteiroapp.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetroFitService {
    private const val BASE_URL = "http://54.161.61.80:80/api/"
//    private const val BASE_URL = "http://192.168.0.2:8080/"
//    private const val BASE_URL = "http://172.20.10.8:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun getApi(token: String? = null): ApiZePorteiro {
        Log.d("RetrofitService", "Criando API client. Token presente: ${token != null}")

        val client = if (!token.isNullOrEmpty()) {
            Log.d("RetrofitService", "Usando client com token: Bearer $token")
            okHttpClient.newBuilder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                        .header("Authorization", "Bearer $token")
                        .build()
                    Log.d("RetrofitService", "Request headers: ${request.headers}")
                    chain.proceed(request)
                }
                .build()
        } else {
            Log.d("RetrofitService", "Usando client sem token")
            okHttpClient
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiZePorteiro::class.java)
    }
}

//    class InterceptorTokenJWT(private val token: String) : Interceptor {
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val requestBuilder = chain.request().newBuilder()
//
//            // Adiciona o cabeçalho de autenticação somente se o token estiver presente
//            requestBuilder.addHeader("Authorization", "Bearer $token")
//
//            return try {
//                chain.proceed(requestBuilder.build())
//            } catch (e: Exception) {
//                throw RuntimeException("Erro ao enviar requisição: ${e.message}", e)
//            }
//        }
//    }
//}

//object RetroFitService {
//    private const val BASE_URL = "http://192.168.0.2:8080/"
//
////    fun getApi(): ApiZePorteiro {
////        val client = OkHttpClient.Builder()
////            .addInterceptor { chain ->
////                val request = chain.request().newBuilder()
////                    .addHeader("Content-Type", "application/json")
////                    .build()
////                chain.proceed(request)
////            }
////            .build()
////
////        return Retrofit.Builder()
////            .baseUrl(BASE_URL)
////            .client(client)
////            .addConverterFactory(GsonConverterFactory.create())
////            .build()
////            .create(ApiZePorteiro::class.java)
////    }
//
//    fun getApi(token: String): ApiZePorteiro {
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(InterceptorTokenJWT(token))
//            .build()
//
//        val client = Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiZePorteiro::class.java)
//
//        return client
//    }
//
//    class InterceptorTokenJWT(val token:String): Interceptor {
//
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val currentRequest = chain.request().newBuilder()
//
//            currentRequest.addHeader("Authorization", "Bearer $token")
//
//            val newRequest = currentRequest.build()
//            return chain.proceed(newRequest)
//        }
//    }
//}