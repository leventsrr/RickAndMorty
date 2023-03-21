package com.leventsurer.rickandmorty.tools.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.leventsurer.rickandmorty.R
import com.leventsurer.rickandmorty.data.model.Result
import com.leventsurer.rickandmorty.databinding.RowLocationBinding

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationHolder>() {


    class LocationHolder(val binding: RowLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    var list = ArrayList<Result>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        val binding =
            RowLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        holder.binding.apply {
            val currentItem = list[position]
            twLocationName.text = currentItem.name
            changeSelectedLocationStyle(position,twLocationName)
            onClickHandler(mcwCharacterRow,position)
        }


    }

    private fun onClickHandler(rowItem:MaterialCardView,position: Int) {
        rowItem.setOnClickListener {
            getLocationId.let {
                if(it != null){
                    it(list[position].id)
                }
            }
            getSelectedLocation.let {
                if (it != null) {
                    it(position)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private var getLocationId: ((locationId: Int) -> Unit)? = null
    fun getLocationId(f: ((locationId: Int) -> Unit)) {
        getLocationId = f
    }

    private var getSelectedLocation: ((position:Int) -> Unit)? = null
    fun getSelectedLocation(f: ((position:Int) -> Unit)) {
        getSelectedLocation = f
    }

    private fun changeSelectedLocationStyle(position: Int,locationText:TextView){
        if(list[position].isSelected){
            locationText.setTextColor(Color.parseColor("#3AB54A"))
        }else{
            locationText.setTextColor(Color.parseColor("#191919"))
        }
    }

}