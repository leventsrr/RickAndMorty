package com.leventsurer.rickandmorty.tools.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        }
        holder.itemView.setOnClickListener {
            getLocationId.let {
                if(it != null){
                    it(list[position].id)
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
}