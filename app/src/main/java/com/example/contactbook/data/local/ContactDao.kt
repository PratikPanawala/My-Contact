package com.example.contactbook.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.contactbook.data.model.Contact

@Dao
interface ContactDao {
    @Query("SELECT * from contact_table ORDER BY first_name")
    fun getAlphabetizedContact(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contacts: List<Contact>)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()
}
