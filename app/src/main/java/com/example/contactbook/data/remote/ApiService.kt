package com.example.contactbook.data.remote

import com.example.contactbook.data.model.Contact
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("contacts.json")
    fun getContact(@Query("alt") alt: String, @Query("token") token: String): Call<List<Contact>>
}