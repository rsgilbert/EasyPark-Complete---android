package com.gilboot.easypark

import com.gilboot.easypark.network.DriverNetwork
import com.gilboot.easypark.network.ParkNetwork
import com.gilboot.easypark.network.VisitNetwork
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

private const val localUrl = "http://10.0.2.2:3000"
private const val baseUrl = "https://gentle-cove-07440.herokuapp.com"
private const val inUseUrl = baseUrl

/**
 * A retrofit service to fetch EasyPark information.
 */
interface Service {
    // get

    @GET("parks")
    suspend fun fetchParks(): List<ParkNetwork>

    @GET("visits")
    suspend fun fetchVisits(): List<VisitNetwork>


    // post

    @FormUrlEncoded
    @POST("parksignup")
    suspend fun postParkSignup(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("tel") tel: String,
        @Field("lat") lat: Double,
        @Field("lng") lng: Double,
        @Field("picture") picture: String
    ): ParkNetwork


    @FormUrlEncoded
    @POST("parklogin")
    suspend fun postParkLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): ParkNetwork


    @FormUrlEncoded
    @POST("driversignup")
    suspend fun postDriverSignup(
        @Field("email") email: String,
        @Field("password") password: String
    ): DriverNetwork

    @FormUrlEncoded
    @POST("driverlogin")
    suspend fun postDriverLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): DriverNetwork


    @FormUrlEncoded
    @POST("visits")
    suspend fun postVisit(
        @Field("parkId")
        parkId: String,

        @Field("numberplate")
        numberplate: String,

        @Field("start")
        start: Long,

        @Field("end")
        end: Long,

        @Field("complete")
        complete: Boolean
    ): VisitNetwork


    // put

    @FormUrlEncoded
    @PUT("visits/{_id}")
    suspend fun putVisit(
        @Path("_id")
        _id: String,

        @Field("parkId")
        parkId: String,

        @Field("numberplate")
        numberplate: String,

        @Field("start")
        start: Long,

        @Field("end")
        end: Long,

        @Field("complete")
        complete: Boolean
    ): VisitNetwork
}

/**
 * Main entry point for network access. Call like `DevByteNetwork.devbytes.getPlaylist()`
 */
private val service: Service by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl(inUseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(Service::class.java)
}

fun getNetworkService() = service
