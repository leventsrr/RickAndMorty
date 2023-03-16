package com.leventsurer.rickandmorty.tools.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leventsurer.rickandmorty.data.model.CharacterDetailModel
import com.leventsurer.rickandmorty.databinding.RowCharacterBinding

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterHolder>() {


    class CharacterHolder(val binding: RowCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    var list = ArrayList<CharacterDetailModel>()
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
            twCharacterName.text = list[position].name
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
        return list.size
    }

    private var moveDetailPage: ((character: CharacterDetailModel) -> Unit)? = null
    fun moveDetailPage(f: ((character: CharacterDetailModel) -> Unit)) {
        moveDetailPage = f
    }
}