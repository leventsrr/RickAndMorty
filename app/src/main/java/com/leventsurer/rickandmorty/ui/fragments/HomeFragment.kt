package com.leventsurer.rickandmorty.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leventsurer.rickandmorty.data.model.CharacterDetailModel
import com.leventsurer.rickandmorty.data.model.Resource
import com.leventsurer.rickandmorty.data.model.Result
import com.leventsurer.rickandmorty.databinding.FragmentHomeBinding
import com.leventsurer.rickandmorty.tools.adapter.CharacterAdapter
import com.leventsurer.rickandmorty.tools.adapter.LocationAdapter
import com.leventsurer.rickandmorty.viewModel.ApiViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val apiViewModel by viewModels<ApiViewModel>()

    private var locationsAdapterList = ArrayList<Result>()
    private var characterAdapterList = ArrayList<CharacterDetailModel>()
    private var characterIdArray = ArrayList<Int>()

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
        getLocations()
        setupLocationAdapter()
        setupCharacterAdapter()
        subscribeLocationsObserve()
        subscribeLocationObserve()
        subscribeCharactersById()
    }

    private fun subscribeCharactersById() {
        apiViewModel.characters.observe(viewLifecycleOwner){
            when(it){
                is Resource.Failure ->{
                    Toast.makeText(requireContext(),it.exception.message,Toast.LENGTH_LONG).show()
                }
                is Resource.Loading ->{
                }
                is Resource.Success ->{
                    characterAdapterList.clear()
                    characterAdapterList.addAll(it.result)
                    characterAdapter.list = characterAdapterList

                }
                else -> {
                    Log.e("control","location observe function in else HomeFragment")
                }
            }
        }
    }


    private fun subscribeLocationsObserve() {
        apiViewModel.locations.observe(viewLifecycleOwner){
            when(it){
                is Resource.Failure ->{
                    Toast.makeText(requireContext(),it.exception.message,Toast.LENGTH_LONG).show()
                }
                is Resource.Loading ->{

                }
                is Resource.Success ->{
                    locationsAdapterList.clear()
                    locationsAdapterList.addAll(it.result.results)
                    locationAdapter.list = locationsAdapterList
                }
                else -> {
                    Log.e("control","location observe function in else HomeFragment")
                }
            }
        }
    }

    private  fun getLocations() {
       runBlocking {
           apiViewModel.getLocations()
       }
    }


    private fun setupCharacterAdapter() {
        binding.rwCharacterList.layoutManager = LinearLayoutManager(requireContext())
        characterAdapter = CharacterAdapter()
        binding.rwCharacterList.adapter = characterAdapter
        characterAdapter.moveDetailPage {
            //val action = HomeFragmentDirections.actionHomeFragmentToCharacterDetailFragment(it)
            // indNavController().navigate(action)
        }
    }

    private fun subscribeLocationObserve() {
        apiViewModel.location.observe(viewLifecycleOwner){
            when(it){
                is Resource.Failure ->{
                    Toast.makeText(requireContext(),it.exception.message,Toast.LENGTH_LONG).show()
                }
                is Resource.Loading ->{
                }
                is Resource.Success ->{
                    for (residentLink in it.result.residents){
                        val characterId = residentLink.split("/").last().toInt()
                        characterIdArray.add(characterId)
                    }

                    runBlocking {
                        apiViewModel.getMultipleCharacterById(characterIdArray)
                        characterIdArray.clear()
                    }

                }
                else -> {
                    Log.e("control","location observe function in else HomeFragment")
                }
            }
        }
    }

    private fun setupLocationAdapter() {
        binding.rwLocationList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        locationAdapter = LocationAdapter()
        binding.rwLocationList.adapter = locationAdapter
        locationAdapter.getLocationId {

            runBlocking {
                Log.e("kontrol","id ye göre istek atıldı")
                apiViewModel.getALocationById(it)

            }

        }
    }


}