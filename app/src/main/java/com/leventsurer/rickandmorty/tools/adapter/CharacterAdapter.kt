package com.leventsurer.rickandmorty.tools.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leventsurer.rickandmorty.R
import com.leventsurer.rickandmorty.data.model.CharacterDetailModel
import com.leventsurer.rickandmorty.databinding.RowCharacterBinding

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterHolder>() {
    private lateinit var context: Context


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
        context = parent.context
        val binding =
            RowCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        holder.binding.apply {
            val currentItem = list[position]
            twCharacterName.text = currentItem.name
            Glide.with(context).load(currentItem.image).into(iwCharacterImage)
            when (currentItem.gender) {
                "Male" -> {
                    iwCharacterGender.setImageResource(R.drawable.male)
                }
                "Female" -> {
                    iwCharacterGender.setImageResource(R.drawable.female)
                }
                else -> {
                    iwCharacterGender.setImageResource(R.drawable.genderless)
                }
            }

        }

        holder.itemView.setOnClickListener {
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

    //redirects to the detail page with the data of the character clicked from the list
    private var moveDetailPage: ((character: CharacterDetailModel) -> Unit)? = null
    fun moveDetailPage(f: ((character: CharacterDetailModel) -> Unit)) {
        moveDetailPage = f
    }
}