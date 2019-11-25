package com.example.contactbook.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.contactbook.R
import com.example.contactbook.databinding.ActivityDetailContactBinding
import com.example.contactbook.viewModel.ContactDetailViewModel


class DetailContactActivity : AppCompatActivity() {
    private lateinit var contactDetailViewModel: ContactDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getIntExtra("id", 0)
        val binding: ActivityDetailContactBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail_contact)
        contactDetailViewModel = ViewModelProvider(this).get(ContactDetailViewModel::class.java)

        contactDetailViewModel.contact.observe(this, Observer {
            binding.textViewEmail.text = it.email
            binding.textViewPhone.text = it.phone_number
            binding.textViewName.text = getString(R.string.contact_name,it.first_name,it.last_name)
        })
        contactDetailViewModel.getContact(id)
    }
}
