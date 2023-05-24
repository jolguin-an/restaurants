package com.example.restaurant.ui.places

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurant.R
import com.example.restaurant.domain.models.ResultsItem


class NearbyPlacesListAdapter(
    private val items: List<ResultsItem>,
) : RecyclerView.Adapter<NearbyPlacesListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlaceName: TextView
        val tvPlaceInfo: TextView
        val tvPlacePrice: TextView

        init {
            tvPlaceName = view.findViewById(R.id.tvPlaceName)
            tvPlaceInfo = view.findViewById(R.id.tvPlaceInfo)
            tvPlacePrice = view.findViewById(R.id.tvPlacePrice)
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_places, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvPlaceName.text = items[position].name
        viewHolder.tvPlaceInfo.text = items[position].reference
        viewHolder.tvPlacePrice.text = items[position].priceLevel.toString()
    }

    override fun getItemCount() = items.size

}