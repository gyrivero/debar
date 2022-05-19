package com.example.barreview.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import com.example.barreview.R


class Dialogs(context: Context) : Dialog(context){

    interface MyDialogListener {
        fun onSelectedAValue(value: Float)
    }

    private lateinit var myDialogListener : MyDialogListener

    fun setMyDialogListener(listener: MyDialogListener) {
        myDialogListener = listener
    }


    fun addFoodReviewDialog() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setCancelable(false)
        this.setContentView(R.layout.layout_add_food_dialog)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val rateBtn = this.findViewById<Button>(R.id.foodRateBtn)
        val cancelBtn = this.findViewById<Button>(R.id.cancelRateBtn)
        val rating = this.findViewById<RatingBar>(R.id.foodDialogRB)
        rateBtn.setOnClickListener{
            myDialogListener.onSelectedAValue(rating.rating)
            this.dismiss()
        }
        cancelBtn.setOnClickListener{
            this.dismiss()
        }

    }

    fun lastFoodReview(ratingValue : Float, dateValue : String) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setCancelable(true)
        this.setContentView(R.layout.layout_last_review_dialog)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val acceptBtn = this.findViewById<Button>(R.id.lastFoodOkBtn)
        val rating = this.findViewById<RatingBar>(R.id.lastFoodRB)
        val date = this.findViewById<TextView>(R.id.lastFoodDateTV)
        rating.rating = ratingValue
        date.text = dateValue
        acceptBtn.setOnClickListener{
            this.dismiss()
        }


    }

}