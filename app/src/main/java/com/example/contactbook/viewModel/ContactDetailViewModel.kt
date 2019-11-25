package com.example.contactbook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.contactbook.data.local.ContactDataBase
import com.example.contactbook.data.model.Contact
import com.example.contactbook.data.remote.ApiClient
import com.example.contactbook.data.remote.ApiService
import com.example.contactbook.dataRepository.ContactRepository
import kotlinx.coroutines.launch


/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */
class ContactDetailViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: ContactRepository
    // LiveData gives us updated words when they change.
//    val contact: LiveData<Contact>
    private val _sortedProducts = MutableLiveData<Contact>()
    val contact: LiveData<Contact> = _sortedProducts

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val wordsDao = ContactDataBase.getDatabase(application).contactDao()
        repository = ContactRepository(
            wordsDao, ApiClient.create()
                .create(ApiService::class.java)
        )
        /* viewModelScope.launch {
             _sortedProducts.value = repository.getContact(id)
         }*/
    }


    /**
     * The implementation of getContact() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on the mainthread, blocking
     * the UI, so we need to handle changing Dispatchers to Dispatchers.IO
     */
    fun getContact(id: Int) {
        viewModelScope.launch {
            _sortedProducts.value = repository.getContact(id)
        }
    }

    /*@BindingAdapter("android:src")
    fun setImageUrl(view: ImageView, url: String?) {
        Glide.with(view.context).load(url).into(view)
    }*/

    fun update(contacts: Contact) = viewModelScope.launch {
        repository.update(contacts)
    }
}
