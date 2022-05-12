package com.example.barreview.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import com.example.barreview.R


class Dialogs(context: Context) : Dialog(context){

    interface MyDialogListener {
        fun onSelectedAValue(value: Float)
    }

    private lateinit var myDialogListener : MyDialogListener

    fun setMyDialogListener(listener: MyDialogListener) {
        myDialogListener = listener
    }


    fun foodDialog() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setCancelable(false)
        this.setContentView(R.layout.layout_food_dialog)
        this.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val rateBtn = this.findViewById(R.id.foodRateBtn) as Button
        val cancelBtn = this.findViewById(R.id.cancelRateBtn) as Button
        val rating = this.findViewById(R.id.foodDialogRB) as RatingBar
        rateBtn.setOnClickListener{
            myDialogListener.onSelectedAValue(rating.rating)
            this.dismiss()
        }
        cancelBtn.setOnClickListener{
            this.dismiss()
        }

    }

}