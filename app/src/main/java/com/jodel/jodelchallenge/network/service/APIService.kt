package com.jodel.jodelchallenge.network.service


import com.jodel.jodelchallenge.model.UserResponse
import com.jodel.jodelchallenge.network.APIEndPoint
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET(APIEndPoint.GET_GIT_USER)
    suspend fun getUsers(@Query("q") username: String, @Query("page") pageNo: Int): UserResponse
}