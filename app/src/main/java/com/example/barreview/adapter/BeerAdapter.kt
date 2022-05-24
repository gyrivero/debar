package com.example.barreview.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.barreview.R
import com.example.barreview.model.Beer
import com.example.barreview.ui.BarFragment

class BeerAdapter (private val fragment : BarFragment) : RecyclerView.Adapter<BeerAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val beerColor: ImageView = view.findViewById(R.id.beerColorIV)
        val beerName: TextView = view.findViewById(R.id.beerNameTV)
        val beerRating : RatingBar = view.findViewById(R.id.beerListRB)
        val beerBrand : TextView = view.findViewById(R.id.beerBrandTV)
        val deleteIB : ImageButton = view.findViewById(R.id.beerDeleteIB)
    }

    var beerList = mutableListOf<Beer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.beer_list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = beerList[position]
        holder.beerColor.setColorFilter(Color.parseColor(item.color),PorterDuff.Mode.SRC_IN)
        holder.beerName.text = item.name
        holder.beerRating.rating = item.rating!!
        holder.beerBrand.text = item.brand
        holder.deleteIB.setOnClickListener{
            fragment.destroyBeer(item.id)
        }
    }

    override fun getItemCount(): Int {
        return if (beerList.size > 0) {
            beerList.size
        } else {
            0
        }
    }

    fun getbarList(): List<Beer> {
        val list : List<Beer> = beerList
        return list
    }


}