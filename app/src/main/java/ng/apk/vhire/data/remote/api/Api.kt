package ng.apk.vhire.data.remote.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject

import ng.apk.vhire.models.JobCategory
import ng.apk.vhire.models.Service
import ng.apk.vhire.models.Session
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by othree on 2/12/18.
 * This registers all the API endpoints in the app
 *
 */

interface Api {

    @get:Headers("content-type: application/json")
    @get:GET("categories/")
    val categories: Call<List<JobCategory>>

    @Headers("content-type: application/json")
    @GET("categories/{id}")
    fun services(@Path("id") id: Int): Call<List<Service>>


    @GET("services/{id}")
    fun service(@Path("id") id: Int): Call<Service>

    @Headers("content-type: application/json")
    @POST("users/login/")
    fun login(@Body data: JsonObject): Call<JsonObject>

    @Headers("content-type: application/json")
    @POST("users/")
    fun signup(@Body data: JsonObject): Call<JsonObject>


    @Headers("content-type: application/json")
    @POST("bookings/")
    fun makebooking(@Body data: String): Call<JsonArray>


    @get:Headers("content-type: application/json")
    @get:GET("bookings/")
    val fetchBookings: Call<JsonArray>


    @Headers("content-type: application/json")
    @POST("sessions/{id}/submit/")
    fun submitSession(@Path("id") id: Int): Call<Session>

    @Headers("content-type: application/json")
    @POST("sessions/{id}/cancel/")
    fun cancelSession(@Path("id") id: Int): Call<Session>

    @Headers("content-type: application/json")
    @POST("sessions/{id}/start/")
    fun startSession(@Path("id") id: Int): Call<Session>

}
