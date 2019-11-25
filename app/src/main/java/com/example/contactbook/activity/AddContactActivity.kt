package com.example.contactbook.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.contactbook.R
import com.example.contactbook.data.model.Contact
import com.example.contactbook.databinding.ActivityAddContactBinding
import com.example.contactbook.viewModel.ContactAddViewModel
import kotlinx.android.synthetic.main.activity_add_contact.*


class AddContactActivity : AppCompatActivity(), ContactAddViewModel.ViewListener {
    companion object {
        const val EXTRA_REPLY = "com.example.contactbook.REPLY"
    }

    private lateinit var contactAddViewModel: ContactAddViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactAddViewModel = ContactAddViewModel(this)
        val binding: ActivityAddContactBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_add_contact)
        binding.contactAddViewModel = contactAddViewModel

    }

    override fun onSaveSuccess() {
        val replyIntent = Intent()
        val email = edit_email.text.toString()
        val firstName = edit_first_name.text.toString()
        val laseName = edit_last_name.text.toString()
        val phoneNumber = edit_mobile_number.text.toString()
        val contact = Contact("", email, false, firstName, laseName, phoneNumber, "", "")
        replyIntent.putExtra(EXTRA_REPLY, contact)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    override fun onError(header: String?, message: String?) {
        val replyIntent = Intent()
        setResult(Activity.RESULT_CANCELED, replyIntent)
    }
}
