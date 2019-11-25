package com.example.contactbook.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactbook.R
import com.example.contactbook.adapter.ContactAdapter
import com.example.contactbook.viewModel.ContactViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ContactAdapter.ContactClickListener {

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contactAdapter = ContactAdapter(this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = contactAdapter

        // Get a new or existing ViewModel from the ViewModelProvider.
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        contactViewModel.allContact.observe(this, Observer { contacts ->
            // Update the cached copy of the words in the adapter.
            contacts?.let { contactAdapter.setContacts(it) }
        })
        contactViewModel.getContact()

    }

    override fun onContactClick(id: Int) {
        val intent = Intent(this@MainActivity, DetailContactActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}
