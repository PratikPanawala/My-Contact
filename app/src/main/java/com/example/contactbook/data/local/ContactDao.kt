package com.example.contactbook.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contactbook.data.model.Contact

@Dao
interface ContactDao {
    @Query("SELECT * from contact_table ORDER BY first_name")
    fun getAlphabetizedContact(): LiveData<List<Contact>>

    @Query("SELECT * from contact_table WHERE id=:id LIMIT 1")
    fun getContactById(id: Int): Contact

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contacts: List<Contact>)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()
}
