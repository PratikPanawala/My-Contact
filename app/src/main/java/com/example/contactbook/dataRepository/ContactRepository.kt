package com.example.contactbook.dataRepository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.contactbook.callbacks.ResultCallback
import com.example.contactbook.data.local.ContactDao
import com.example.contactbook.data.model.Contact
import com.example.contactbook.data.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

class ContactRepository(private val contactDao: ContactDao, private val apiService: ApiService) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allContact: LiveData<List<Contact>> = contactDao.getAlphabetizedContact()

    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    suspend fun insert(contacts: List<Contact>) {
        contactDao.insert(contacts)
    }

    fun getContact(resultCallback: ResultCallback) {
        apiService.getContact("media", "7275ae23-3a65-49b6-8fe5-f8ff0c0cbc75").enqueue(object :
            Callback<List<Contact>> {

            override fun onFailure(call: Call<List<Contact>>, t: Throwable) {
                Log.e("ContactApi", "Api Call failed")
                resultCallback.onFailure("Api Call Failed")
            }

            override fun onResponse(
                call: Call<List<Contact>>,
                response: Response<List<Contact>>
            ) {
                if (response.body() != null) {
                    val contactMutableList = response.body() as List<Contact>
                    resultCallback.onSuccess(contactMutableList)
                } else {
                    Log.e("ContactApi", "Body is empty")
                    resultCallback.onFailure("Body is empty")
                }
            }
        })
    }
}