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

    private val apiViewModel by viewModels<ApiViewModel>()

    private var locationsAdapterList = ArrayList<Result>()
    private var characterAdapterList = ArrayList<CharacterDetailModel>()
    private var characterIdArray = ArrayList<Int>()

    private var pageNumber = 1
    private var isLoading = true
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

    private fun getLocations(pageNumber:Int) {
        binding.pbLocationProgressBar.visibility = VISIBLE
        binding.rwLocationList.visibility = GONE

        runBlocking {
            apiViewModel.getLocations(pageNumber)
        }
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

    private fun setupLocationAdapter() {
        binding.rwLocationList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        locationAdapter = LocationAdapter()
        binding.rwLocationList.adapter = locationAdapter

        locationAdapterOnClickListener()

        locationAdapterScrollListener(binding.rwLocationList)

    }

    private fun locationAdapterOnClickListener() {
        locationAdapter.getLocationId {
            binding.pbProgressBar.visibility = VISIBLE
            runBlocking {
                Log.e("kontrol", "id ye göre istek atıldı")
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

    private fun locationAdapterScrollListener(recyclerView:RecyclerView) {
        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                if(!recyclerView.canScrollHorizontally(1)){
                    if(pageNumber == 7){
                        Toast.makeText(requireContext(),"Son Sayfaya Ulaştınız!",Toast.LENGTH_LONG).show()
                    }else{
                        pageNumber++

                        getLocations(pageNumber)
                        recyclerView.layoutManager?.scrollToPosition(0)
                    }
                }else if(!recyclerView.canScrollHorizontally(-1)){
                    if(pageNumber == 1){
                        recyclerView.isNestedScrollingEnabled = false
                        Toast.makeText(requireContext(),"$pageNumber. Sayfaya Ulaştınız!",Toast.LENGTH_LONG).show()
                    }else{
                        pageNumber--
                        getLocations(pageNumber)
                    }

                }
                super.onScrollStateChanged(recyclerView, newState)
            }
            /*override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
               // if (dx>0){
                    val visibleItemCount = binding.rwLocationList.layoutManager?.childCount
                    val pastVisibleItem = (binding.rwLocationList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    val total = locationAdapter.itemCount

                    if(isLoading){
                        if (visibleItemCount != null) {
                            if((visibleItemCount + pastVisibleItem) >= total){
                                isLoading = false
                                Log.e("kontrol","sayfa sonu")
                                Log.e("kontrol","sayfa numarası önce $pageNumber")
                                pageNumber++
                                Log.e("kontrol","sayfa numarası sonra $pageNumber")
                                getLocations(pageNumber)

                            }
                        }

                    }
               // }


                super.onScrolled(recyclerView, dx, dy)
            }*/
        })
    }


}