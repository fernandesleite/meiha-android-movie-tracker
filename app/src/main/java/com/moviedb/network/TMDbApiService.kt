package com.moviedb.network

import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "d686c93461aba88b229359f910bd76d1"

// Interceptor to add the api_key parameter to retrofit
class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val httpUrl = request.url.newBuilder().addQueryParameter("api_key", API_KEY).build()
        request = request.newBuilder().url(httpUrl).build()
        return chain.proceed(request)
    }
}

private val client : OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(ApiInterceptor())
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(client)
    .build()

interface TMDbApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies() : TMDbMoviesResponse
}

//Lazy public object so only one instance is created when called
object TMDbApi {
    val retrofitService : TMDbApiService by lazy {
        retrofit.create(TMDbApiService::class.java)
    }
}