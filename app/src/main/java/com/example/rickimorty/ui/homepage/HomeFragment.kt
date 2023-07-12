package com.example.rickimorty.ui.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickimorty.MortyApplication
import com.example.rickimorty.data.models.CharacterDomain
import com.example.rickimorty.databinding.FragmentHomeBinding
import com.example.rickimorty.local.database.CharacterDatabase
import com.example.rickimorty.remote.MortyApi
import com.example.rickimorty.data.repository.CharacterRepository


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
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchCharacter(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchCharacter(newText)
                return false
            }
        })


    }

        override fun onDestroyView() {
            super.onDestroyView()
            binding = null
        }

        private fun adapterOnClick(characterDomain: CharacterDomain){
            val name = characterDomain.name
            val status = characterDomain.status
            val species = characterDomain.species
            val gender = characterDomain.gender
            val origin = characterDomain.origin.name
            val location = characterDomain.location.name
            val image = characterDomain.image

            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                name,status,species,gender,origin,location,image
            )

            findNavController().navigate(action)
        }

        private fun setUpRecycler() {
            val layoutManager = LinearLayoutManager(requireContext())
            val adapter = RecyclerAdapter{ character -> adapterOnClick(character)}
            binding?.recyclerView?.adapter = adapter
            binding?.recyclerView?.layoutManager = layoutManager
            binding?.recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    //check if scrolling up or down
                    if (dy > 0) {
                        //Scrolling down
                        val visibleItemCount = layoutManager.childCount
                        val totalItemCount = layoutManager.itemCount
                        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                        //check if reached the last items
                        if (visibleItemCount + firstVisibleItemPosition
                            >= totalItemCount && firstVisibleItemPosition >= 0
                        ) {
                            viewModel.loadNextCharacters()
                        }

                    }
                }
            })


            viewModel.isLoading.observe(this.viewLifecycleOwner) { isLoading ->
                binding?.progressBar?.visibility =
                    if (isLoading) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            }
            viewModel.characters.observe(this.viewLifecycleOwner) { characters ->
                characters.let {
                    adapter.submitList(it as MutableList<CharacterDomain>)
                    adapter.setCharacterList(characters)
                }
            }
        }

}