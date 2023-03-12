package com.leventsurer.rickandmorty.tools.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leventsurer.rickandmorty.databinding.RowCharacterBinding

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterHolder>() {


    class CharacterHolder(val binding: RowCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    var list = ArrayList<String>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val binding =
            RowCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        holder.binding.apply {
            twCharacterName.text = "Rick"
        }

        holder.itemView.setOnClickListener{
            moveDetailPage.let {
                if (it != null) {
                    it(list[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return 6
    }

    private var moveDetailPage: ((character: String) -> Unit)? = null
    fun moveDetailPage(f: ((character: String) -> Unit)) {
        moveDetailPage = f
    }
}