package com.example.rickimorty.ui.detailspage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.rickimorty.R
import com.example.rickimorty.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

 private var binding : FragmentDetailsBinding? = null
 private val args: DetailsFragmentArgs by navArgs()

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
  populateViews()
 }

 override fun onDestroyView() {
  super.onDestroyView()
  binding = null
 }
 private fun populateViews(){
  binding?.apply {
   dTxtName.text= args.name
   dTxtStatus.text=args.status
   dTxtSpecies.text=args.species
   dTxtGender.text=args.gender
   dTxtOrigin.text=args.origin
   dTxtLocation.text=args.location

   val imageUrl = args.image
   Glide.with(requireActivity())
    .load(imageUrl)
    .into(dImgVw)

  }
 }
}