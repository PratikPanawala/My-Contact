package com.example.contactbook.callbacks

import com.example.contactbook.data.model.Contact

interface ResultCallback {
    fun onSuccess(result: List<Contact>)
    fun onFailure(message: String)
}