package com.example.storyapp.data.api.remote


import com.example.storyapp.data.api.response.DetailStoryResponse
import com.example.storyapp.data.api.response.FileUploadResponse
import com.example.storyapp.data.api.response.LoginResponse
import com.example.storyapp.data.api.response.RegisterResponse
import com.example.storyapp.data.api.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int?
    ): StoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStories(
        @Path("id") id: String
    ) : DetailStoryResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part,
        @Part("lat") lat: Float? = null,
        @Part("lon") lon: Float? = null
    ): FileUploadResponse

//    @Multipart
//    @POST("stories")
//    suspend fun addStory(
//        @Part("description") description: RequestBody,
//        @Part file: MultipartBody.Part,
//    ): FileUploadResponse
}