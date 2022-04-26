package com.example.barreview.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.barreview.model.Bar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Repo {
    val database = FirebaseDatabase.getInstance().getReference("Bar")


    fun getData() : LiveData<MutableList<Bar>> {
        val mutableList = MutableLiveData<MutableList<Bar>>()
        val list = mutableListOf<Bar>()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot!!.exists()){
                    list.clear()
                    for (e in dataSnapshot.children){
                        val post = e.getValue(Bar::class.java)
                        list.add(post!!)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        database.addValueEventListener(postListener)

        mutableList.value = list
        return mutableList
    }

    fun addBar() {
        // write from the database
        val bar :Bar = Bar(2,"Blaadasdasdadsadasdsadasdr","Cordoba","Palermo",3.0f)
        database.child("Bar").push().setValue(bar)
    }

}