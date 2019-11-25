package com.example.contactbook.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactbook.R
import com.example.contactbook.adapter.ContactAdapter
import com.example.contactbook.data.model.Contact
import com.example.contactbook.viewModel.ContactViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ContactAdapter.ContactClickListener {
    private val newContactActivityRequestCode = 1
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

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddContactActivity::class.java)
            startActivityForResult(intent, newContactActivityRequestCode)
        }

    }

    override fun onContactClick(id: Int) {
        val intent = Intent(this@MainActivity, DetailContactActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newContactActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val contact: Contact =
                data?.getSerializableExtra(AddContactActivity.EXTRA_REPLY) as Contact
            contactViewModel.insert(contact)
            Toast.makeText(
                applicationContext,
                R.string.user_added,
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
