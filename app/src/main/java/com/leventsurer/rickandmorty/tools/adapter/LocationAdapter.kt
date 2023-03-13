package com.leventsurer.rickandmorty.tools.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leventsurer.rickandmorty.data.model.Results
import com.leventsurer.rickandmorty.databinding.RowLocationBinding

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationHolder>() {


    class LocationHolder(val binding: RowLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    var list = ArrayList<Results>()
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
            twLocationName.text = "Earth"
        }
    }

    override fun getItemCount(): Int {
        return 6
    }
}