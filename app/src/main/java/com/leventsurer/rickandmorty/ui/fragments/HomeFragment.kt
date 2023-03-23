package com.leventsurer.rickandmorty.ui.fragments

import android.os.Binder
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leventsurer.rickandmorty.data.model.CharacterDetailModel
import com.leventsurer.rickandmorty.data.model.Resource
import com.leventsurer.rickandmorty.data.model.Result
import com.leventsurer.rickandmorty.databinding.FragmentHomeBinding
import com.leventsurer.rickandmorty.tools.adapter.CharacterAdapter
import com.leventsurer.rickandmorty.tools.adapter.LocationAdapter
import com.leventsurer.rickandmorty.viewModel.ApiViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    //view models
    private val apiViewModel by viewModels<ApiViewModel>()

    //adapter lists
    private var locationsAdapterList = ArrayList<Result>()
    private var characterAdapterList = ArrayList<CharacterDetailModel>()
    private var characterIdArray = ArrayList<Int>()

    //default page number for location request
    private var pageNumber = 1

    //adapters
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var locationAdapter: LocationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocations(pageNumber)
        setupLocationAdapter()
        setupCharacterAdapter()
        subscribeLocationsObserve()
        subscribeLocationObserve()
        subscribeCharactersById()
    }

    //Listens for data obtained by aggregating and requesting a list of multiple characters
    private fun subscribeCharactersById() {
        apiViewModel.characters.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    binding.pbProgressBar.visibility = GONE
                    characterAdapterList.clear()
                    characterAdapterList.addAll(it.result)
                    characterAdapter.list = characterAdapterList

                }
                else -> {
                    Log.e("control", "location observe function in else HomeFragment")
                }
            }
        }
    }

    //Listens for the locations to be listed in the horizontal list
    private fun subscribeLocationsObserve() {
        apiViewModel.locations.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    locationsAdapterList.clear()
                    locationsAdapterList.addAll(it.result.results)
                    locationAdapter.list = locationsAdapterList
                    binding.pbLocationProgressBar.visibility = GONE
                    binding.rwLocationList.visibility = VISIBLE
                }
                else -> {
                    Log.e("control", "location observe function in else HomeFragment")
                }
            }
        }
    }

    //Assigns a location request according to the given page number
    private fun getLocations(pageNumber: Int) {
        binding.pbLocationProgressBar.visibility = VISIBLE
        binding.rwLocationList.visibility = GONE

        runBlocking {
            apiViewModel.getLocations(pageNumber)
        }
    }

    //Sets up the character list
    private fun setupCharacterAdapter() {
        binding.rwCharacterList.layoutManager = LinearLayoutManager(requireContext())
        characterAdapter = CharacterAdapter()
        binding.rwCharacterList.adapter = characterAdapter
        characterAdapter.moveDetailPage {
            val action = HomeFragmentDirections.actionHomeFragmentToCharacterDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    //When one of the locations in the horizontal list is clicked, it listens for the response of the location request to get the characters in it
    private fun subscribeLocationObserve() {
        apiViewModel.location.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    for (residentLink in it.result.residents) {
                        val characterId = residentLink.split("/").last().toInt()
                        characterIdArray.add(characterId)
                    }

                    runBlocking {
                        apiViewModel.getMultipleCharacterById(characterIdArray)
                        characterIdArray.clear()
                    }

                }
                else -> {
                    Log.e("control", "location observe function in else HomeFragment")
                }
            }
        }
    }

    //Sets up the location list
    private fun setupLocationAdapter() {
        binding.rwLocationList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        locationAdapter = LocationAdapter()
        binding.rwLocationList.adapter = locationAdapter

        locationAdapterOnClickListener()

        locationAdapterScrollListener(binding.rwLocationList)

    }

    //Performs the actions to be performed when clicking on the elements listed in RecyclerView
    private fun locationAdapterOnClickListener() {
        locationAdapter.getLocationId {
            binding.pbProgressBar.visibility = VISIBLE
            runBlocking {
                Log.e("control", "id ye göre istek atıldı")
                apiViewModel.getALocationById(it)
            }
        }

        locationAdapter.getSelectedLocation { position ->
            for (i in 0 until locationsAdapterList.size) {
                locationsAdapterList[i].isSelected = i == position
            }
            locationAdapter.list = locationsAdapterList
        }
    }

    //RecyclerView also performs lazy load to fetch new locations
    private fun locationAdapterScrollListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    AbsListView.OnScrollListener.SCROLL_STATE_FLING -> {

                    }
                    AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL -> {

                    }
                    else -> {
                        if (!recyclerView.canScrollHorizontally(1)) {
                            if (pageNumber == 7) {
                                Toast.makeText(
                                    requireContext(),
                                    "Son Sayfaya Ulaştınız!",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                pageNumber++

                                getLocations(pageNumber)
                                recyclerView.layoutManager?.scrollToPosition(0)
                            }
                        } else if (!recyclerView.canScrollHorizontally(-1)) {
                            if (pageNumber == 1) {
                                recyclerView.isNestedScrollingEnabled = false
                                Toast.makeText(
                                    requireContext(),
                                    "$pageNumber. Sayfaya Ulaştınız!",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                pageNumber--
                                getLocations(pageNumber)
                            }

                        }
                    }
                }
            }


        })
    }


}