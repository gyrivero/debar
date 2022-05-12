package com.example.barreview.data.barlist

import com.example.barreview.model.Bar
import com.example.barreview.model.Beer
import com.example.barreview.model.FoodReview
import com.example.barreview.util.DbConstants
import com.example.barreview.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await


class BarListDatasource : IBarListDatasource {

    override suspend fun fetchBarList(): Resource<MutableList<Bar>> {
        val list = mutableListOf<Bar>()
        val querySnapshot = FirebaseFirestore.getInstance().collection(DbConstants.BARS).orderBy(DbConstants.CREATED_AT,Query.Direction.ASCENDING).get().await()
        for (document in querySnapshot.documents) {
            val bar = Bar(document.id,document.getString(DbConstants.NAME),document.getString(DbConstants.ADDRESS),document.getString(DbConstants.NEIGHBORHOOD),
                document.getDouble(DbConstants.RATING)?.toFloat(),document.getDate(DbConstants.CREATED_AT))
            list.add(bar)
            //list.add(document.toObject(Bar::class.java))
        }

        return Resource.Success(list)
    }

    override suspend fun deleteBar(id: String) {
        FirebaseFirestore.getInstance().collection(DbConstants.BARS).document(id).delete().await()
    }


}