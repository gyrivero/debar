package com.example.barreview.data

import android.content.ContentValues.TAG
import android.util.Log
import com.example.barreview.model.Bar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Datasource {
    companion object{

        fun createDataSet(): MutableList<Bar>{


            var database = FirebaseDatabase.getInstance().getReference("Bar")

            val list = ArrayList<Bar>()

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
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                }
            }

            database.addValueEventListener(postListener)

            return list
        }
    }
}