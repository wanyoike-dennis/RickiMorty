package com.example.rickimorty.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.rickimorty.MortyApplication
import com.example.rickimorty.ui.homepage.RecyclerAdapter
import com.example.rickimorty.data.models.CharacterDomain
import com.example.rickimorty.databinding.FragmentHomeBinding
import com.example.rickimorty.local.database.CharacterDatabase
import com.example.rickimorty.remote.MortyApi
import com.example.rickimorty.data.repository.CharacterRepository
import com.example.rickimorty.ui.homepage.MyViewModel
import com.example.rickimorty.ui.homepage.MyViewModelFactory
import com.example.rickimorty.utils.Network


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
        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = RecyclerAdapter()
        binding?.recyclerView?.adapter = adapter
        binding?.recyclerView?.layoutManager= layoutManager
        binding?.recyclerView?.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //check if scrolling up or down
                if (dy > 0){
                    //Scrolling down
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    //check if reached the last items
                    if (visibleItemCount + firstVisibleItemPosition
                        >= totalItemCount && firstVisibleItemPosition >= 0){
                        viewModel.loadNextCharacters()
                    }

                }
            }
        })


        viewModel.isLoading.observe(this.viewLifecycleOwner){
            isLoading ->
            binding?.progressBar?.visibility =
                if (isLoading){
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }




        if (Network.isNetworkAvailable(requireContext())) {
            viewModel.characters.observe(this.viewLifecycleOwner) { characters ->
                characters.let {
                    adapter.submitList(it as MutableList<CharacterDomain>)
                }
            }

        }
        else{
            Toast.makeText(requireContext(),"No internet connection",Toast.LENGTH_LONG).show()
        }
    }
    private fun onScroll(){
        val layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //check if scrolling up or down
                if (dy > 0){
                    //Scrolling down
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    //check if reached the last items
                    if (visibleItemCount + firstVisibleItemPosition
                        >= totalItemCount && firstVisibleItemPosition >= 0){
                            viewModel.loadNextCharacters()
                    }

                }
            }
        })
    }
}