package com.leventsurer.rickandmorty.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leventsurer.rickandmorty.R
import com.leventsurer.rickandmorty.databinding.FragmentCharacterDetailBinding
import com.leventsurer.rickandmorty.databinding.FragmentHomeBinding

class CharacterDetailFragment : Fragment() {
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding: FragmentCharacterDetailBinding get() = _binding!!
    lateinit var characterName : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleArguments()
    }

    private fun handleArguments() {
        arguments.let {
            characterName =
                it?.let { it1 -> CharacterDetailFragmentArgs.fromBundle(it1).character }.toString()
        }
    }


}