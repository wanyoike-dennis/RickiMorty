package com.example.rickimorty.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.rickimorty.MortyApplication
import com.example.rickimorty.ui.homepage.RecyclerAdapter
import com.example.rickimorty.data.models.CharacterDomain
import com.example.rickimorty.databinding.FragmentHomeBinding
import com.example.rickimorty.local.database.CharacterDatabase
import com.example.rickimorty.remote.MortyApi
import com.example.rickimorty.data.repository.CharacterRepository
import com.example.rickimorty.ui.homepage.MyViewModel
import com.example.rickimorty.ui.homepage.MyViewModelFactory


class HomeFragment : Fragment() {

    private val apiService = MortyApi
    private val database = CharacterDatabase
    private var binding : FragmentHomeBinding? = null
    private val viewModel : MyViewModel by activityViewModels {
        MyViewModelFactory(
            CharacterRepository(apiService, database.getDatabase(requireContext()).characterDao()),
            (activity?.application as MortyApplication).database.characterDao())
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentHomeBinding.inflate(inflater,container,false)
        binding= fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding= null
    }


    private fun setUpRecycler(){


        viewModel.isLoading.observe(this.viewLifecycleOwner){
            isLoading ->
            binding?.progressBar?.visibility =
                if (isLoading){
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }


        val adapter = RecyclerAdapter()
        binding?.recyclerView?.adapter = adapter
        viewModel.characters.observe(this.viewLifecycleOwner){
                characters -> characters.let {
            adapter.submitList(it as MutableList<CharacterDomain>)
        }
        }
    }

}