package com.leventsurer.rickandmorty.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.leventsurer.rickandmorty.R
import com.leventsurer.rickandmorty.databinding.FragmentSplashBinding
import com.leventsurer.rickandmorty.viewModel.SharedPreferencesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding get() = _binding!!
    private var isLogin:Boolean = false
    private val sharedPrefViewModel by viewModels<SharedPreferencesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getIsLoginInfo()
        onClickHandler()
    }

    @SuppressLint("SetTextI18n")
    private fun getIsLoginInfo() {
        isLogin = sharedPrefViewModel.readIsLoginInfo()
        if (isLogin) binding.twGreeting.text = "Hello!" else binding.twGreeting.text = "Welcome!"
        sharedPrefViewModel.writeIsLoginInfo(true)
    }

    private fun onClickHandler() {
        binding.apply {
            btnNavigateHome.setOnClickListener {
                val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }
    }


}