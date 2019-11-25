package com.example.contactbook.viewModel

import android.widget.EditText
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.example.contactbook.BR
import com.example.contactbook.R


/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */
class ContactAddViewModel(private val mListener: ViewListener) : BaseObservable() {

    var mFirstName: String = ""
    var mLastName: String = ""
    var mPhone: String = ""
    var mEmail: String = ""

    @Bindable
    var emailError: Int = -1
        set(error) {
            field = error
            notifyPropertyChanged(BR.emailError)
        }

    @Bindable
    var firstNameError: Int = -1
        set(error) {
            field = error
            notifyPropertyChanged(BR.firstNameError)
        }
    @Bindable
    var phoneError: Int = -1
        set(error) {
            field = error
            notifyPropertyChanged(BR.phoneError)
        }

    @Bindable
    var lastNameError: Int = -1
        set(error) {
            field = error
            notifyPropertyChanged(BR.lastNameError)
        }

    companion object {
        @JvmStatic
        @BindingAdapter("app:error")
        fun setError(editText: EditText, strOrResId: Any?) =
            if ((strOrResId as Int) != -1) {
                editText.error = editText.context.getString(strOrResId)
            } else {
                editText.error = null
            }
    }

    fun onSaveClicked() {
        if (isInputValid()) {
            mListener.onSaveSuccess()
        }
    }

    private fun isInputValid(): Boolean {
        return isFirstNameValid() &&
                isLastNameValid() &&
                isPhoneValid() &&
                isEmailValid()
    }

    private fun isEmailValid(): Boolean {

        return if (android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            emailError = -1
            true
        } else {
            emailError = R.string.error_invalid_email
            false
        }
    }

    private fun isPhoneValid(): Boolean {

        return if (android.util.Patterns.PHONE.matcher(mPhone).matches()) {
            phoneError = -1
            true
        } else {
            phoneError = R.string.error_invalid_phone
            false
        }
    }

    private fun isFirstNameValid(): Boolean {

        return if (mFirstName.isNotEmpty() && mFirstName.length > 3) {
            firstNameError = -1
            true
        } else {
            firstNameError = R.string.error_invalid_phone
            false
        }
    }

    private fun isLastNameValid(): Boolean {

        return if (mLastName.isNotEmpty() && mLastName.length > 3) {
            lastNameError = -1
            true
        } else {
            lastNameError = R.string.error_invalid_phone
            false
        }
    }

    interface ViewListener {
        fun onSaveSuccess()
        fun onError(header: String?, message: String?)
    }
}
