package com.example.contactbook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.contactbook.callbacks.ResultCallback
import com.example.contactbook.data.local.ContactDataBase
import com.example.contactbook.data.model.Contact
import com.example.contactbook.data.remote.ApiClient
import com.example.contactbook.data.remote.ApiService
import com.example.contactbook.dataRepository.ContactRepository
import kotlinx.coroutines.launch


/**
 * View Model to keep a reference to the Contact repository and
 * an up-to-date list of all contacts.
 */
class ContactViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: ContactRepository
    // LiveData gives us updated words when they change.
    val allContact: LiveData<List<Contact>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val wordsDao = ContactDataBase.getDatabase(application).contactDao()
        repository = ContactRepository(
            wordsDao, ApiClient.create()
                .create(ApiService::class.java)
        )
        allContact = repository.allContact
    }


    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on the mainthread, blocking
     * the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called viewModelScope which we
     * can use here.
     */
    fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }

    fun insert(contacts: List<Contact>) = viewModelScope.launch {
        repository.insert(contacts)
    }

    fun getContact() = viewModelScope.launch {
        repository.getContact(object : ResultCallback {
            override fun onSuccess(result: List<Contact>) {
                insert(result)
            }

            override fun onFailure(message: String) {
            }
        })
    }
}
