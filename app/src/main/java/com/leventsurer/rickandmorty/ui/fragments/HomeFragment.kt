package com.leventsurer.rickandmorty.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leventsurer.rickandmorty.R
import com.leventsurer.rickandmorty.databinding.FragmentHomeBinding
import com.leventsurer.rickandmorty.databinding.FragmentSplashBinding
import com.leventsurer.rickandmorty.tools.adapter.CharacterAdapter
import com.leventsurer.rickandmorty.tools.adapter.LocationAdapter


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private var locationsAdapterList = ArrayList<String>()
    private var characterList = ArrayList<String>()

    private lateinit var characterAdapter : CharacterAdapter
    private lateinit var locationAdapter : LocationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLocationAdapter()
        setupCharacterAdapter()
    }


    private fun setupCharacterAdapter() {
        binding.rwCharacterList.layoutManager = LinearLayoutManager(requireContext())
        characterAdapter = CharacterAdapter()
        binding.rwCharacterList.adapter = characterAdapter
        characterAdapter.moveDetailPage {
            val action = HomeFragmentDirections.actionHomeFragmentToCharacterDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun setupLocationAdapter() {
        binding.rwLocationList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        locationAdapter = LocationAdapter()
        binding.rwLocationList.adapter = locationAdapter
    }


}