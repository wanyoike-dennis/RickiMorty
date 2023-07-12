package com.example.rickimorty.ui.detailspage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickimorty.R
import com.example.rickimorty.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

 private var binding : FragmentDetailsBinding? = null

 override fun onCreateView(
  inflater: LayoutInflater,
  container: ViewGroup?,
  savedInstanceState: Bundle?
 ): View? {
  val fragmentBinding = FragmentDetailsBinding.inflate(inflater,container,false)
  binding = fragmentBinding
  return fragmentBinding.root
 }

 override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
  super.onViewCreated(view, savedInstanceState)
 }

 override fun onDestroyView() {
  super.onDestroyView()
  binding = null
 }
}