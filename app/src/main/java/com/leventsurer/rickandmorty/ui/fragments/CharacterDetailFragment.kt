package com.leventsurer.rickandmorty.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.leventsurer.rickandmorty.data.model.CharacterDetailModel
import com.leventsurer.rickandmorty.data.model.Location
import com.leventsurer.rickandmorty.data.model.Origin
import com.leventsurer.rickandmorty.databinding.FragmentCharacterDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding: FragmentCharacterDetailBinding get() = _binding!!

    //view models
    private lateinit var characterDetailModel: CharacterDetailModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleArguments()
        setupUi()
        onClickHandler()
    }

    //performs the events of clicking on the buttons on the screen
    private fun onClickHandler() {
        binding.apply {
            iwBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    //allows data from the previous page to be linked to the interface
    @SuppressLint("SetTextI18n")
    private fun setupUi() {
        val origin = Origin(characterDetailModel.origin?.name).name
        val location = Location(characterDetailModel.location?.name).name
        val episodes = arrayListOf<String>()
        for (episode in characterDetailModel.episode) {
            episodes.add(episode.split("/").last())
        }
        val date = characterDetailModel.created?.let { modifyDateLayout(it) }

        binding.apply {
            twCharacterStatus.text = characterDetailModel.status
            twCharacterName.text = characterDetailModel.name
            twCharacterSpecy.text = characterDetailModel.species
            twCharacterGender.text = characterDetailModel.gender
            twCharacterOrigin.text = origin
            twCharacterLocation.text = location
            twCharacterEpisodes.text = episodes.toString()
            twCharacterCreatedAt.text = "${date?.get(0)} , ${date?.get(1)}"
            Glide.with(requireContext()).load(characterDetailModel.image).into(iwCharacterImage)
        }
    }

    //Pulls the submitted model from the homepage
    private fun handleArguments() {
        arguments?.let {
            val argsModel = CharacterDetailFragmentArgs.fromBundle(it).characterDetailModel
            characterDetailModel = argsModel


        }
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(ParseException::class)
    private fun modifyDateLayout(date: String): List<String> {

        val allDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date)
        val parsedDate = allDate.let { SimpleDateFormat("dd.MMM.yyyy HH:mm:ss").format(it) }
        return parsedDate.split(" ")
    }


}