package com.example.barreview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.barreview.BarListFragment
import com.example.barreview.BarListFragmentDirections
import com.example.barreview.R
import com.example.barreview.model.Bar

class BarAdapter (private val barList: List<Bar>, private val fragment : BarListFragment) : RecyclerView.Adapter<BarAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.itemBarTV)
        val ratingBar: RatingBar = view.findViewById(R.id.itemRB)
        val imageButton : ImageButton = view.findViewById(R.id.itemDeleteIB)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.bar_list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = barList[position]
        holder.textView.text = item.name
        holder.ratingBar.rating = item.rating
        holder.imageButton.setOnClickListener{
            fragment.destroyBar(position)
        }
        holder.itemView.setOnClickListener{
            val action = BarListFragmentDirections.actionBarListFragmentToBarFragment(id = item.id)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return barList.size
    }
}