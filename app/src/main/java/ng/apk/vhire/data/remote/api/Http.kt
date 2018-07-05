package ng.apk.vhire.data.remote.api

import android.content.Context

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import es.dmoral.prefs.Prefs

import ng.apk.vhire.models.JobCategory
import ng.apk.vhire.models.Service
import ng.apk.vhire.models.Session
import okhttp3.Interceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import java.io.IOException


/**
 * Created by othree on 2/12/18.
 * This contains all the functions for making api calls to the backend
 */

class Http(private val context: Context?) {

    private val BASE_URL = "https://socialist-donair-73781.herokuapp.com/api/v1/"
    private val api: Api

    val categories: Call<List<JobCategory>>
        get() = this.api.categories
    val bookings: Call<JsonArray>
        get() = this.api.fetchBookings

    init {
        val build = GsonBuilder()
                .setLenient()
                .create()

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain?): okhttp3.Response {

                val token = Prefs.with(context!!).read("token", "")
                val request = chain!!.request().newBuilder().addHeader("Authorization", "Token $token").build()
                return chain.proceed(request)

            }

        })

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(build))
                .client(httpClient.build())
                .build()

        this.api = retrofit.create(Api::class.java)

    }

    fun login(data: JsonObject): Call<JsonObject> {
        return this.api.login(data)
    }

    fun signup(data: JsonObject): Call<JsonObject> {
        return this.api.signup(data)
    }

    fun services(id: Int): Call<List<Service>> {
        return this.api.services(id)
    }

    fun getService(id: Int): Call<Service> {
        return this.api.service(id)
    }

    fun makeBooking(data: String): Call<JsonArray> {
        return this.api.makebooking(data)
    }


    fun submitSession(id: Int): Call<Session> {
        return this.api.submitSession(id)
    }

    fun cancelSession(id: Int): Call<Session> {
        return this.api.cancelSession(id)
    }

    fun startSession(id: Int): Call<Session> {
        return this.api.startSession(id)
    }
}
