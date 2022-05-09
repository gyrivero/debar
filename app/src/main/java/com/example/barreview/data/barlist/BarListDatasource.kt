package com.example.barreview.data.barlist

import com.example.barreview.model.Bar
import com.example.barreview.util.DbConstants
import com.example.barreview.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class BarListDatasource : IBarListDatasource {

    override suspend fun fetchBarList(): Resource<MutableList<Bar>> {
        val list = mutableListOf<Bar>()
        val querySnapshot = FirebaseFirestore.getInstance().collection(DbConstants.BARS).get().await()
        for (document in querySnapshot.documents) {
            val bar = Bar(document.id,document.getString(DbConstants.NAME),document.getString(DbConstants.ADDRESS),document.getString(DbConstants.NEIGHBORHOOD),
                document.getDouble(DbConstants.RATING)?.toFloat())
            list.add(bar)
            //list.add(document.toObject(Bar::class.java))
        }

        return Resource.Success(list)
    }

    override suspend fun deleteBar(id: String) {
        FirebaseFirestore.getInstance().collection(DbConstants.BARS).document(id).delete().await()
    }


}