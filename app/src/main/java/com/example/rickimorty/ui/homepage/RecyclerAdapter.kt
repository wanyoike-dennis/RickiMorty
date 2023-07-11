package com.example.rickimorty.ui.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickimorty.R
import com.example.rickimorty.data.models.CharacterDomain

class RecyclerAdapter :
    ListAdapter<CharacterDomain, RecyclerAdapter.MyViewHolder>(CharacterDiffCallback()) {

    private var characterList : List<CharacterDomain> = emptyList()
     class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         private val image:ImageView = itemView.findViewById(R.id.img_character)
        private val characterName:TextView = itemView.findViewById(R.id.txt_title)
         private val status:TextView = itemView.findViewById(R.id.txt_status)
         private val species:TextView = itemView.findViewById(R.id.txt_species)
         private val gender:TextView = itemView.findViewById(R.id.txt_gender)
         private val origin:TextView = itemView.findViewById(R.id.txt_origin)
         private val location:TextView = itemView.findViewById(R.id.txt_location)
        private var currentCharacter: CharacterDomain? = null

        fun bind(character: CharacterDomain){

            Glide.with(itemView)
                .load(character.image)
                .into(image)

            currentCharacter=character
            characterName.text = character.name
            status.text=character.status
            species.text=character.species
            gender.text=character.gender
            origin.text=character.origin.name
            location.text= character.location.name

        }
    }

    fun setCharacterList(character: List<CharacterDomain>){
        characterList=character
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item,parent,false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }
}

class CharacterDiffCallback: DiffUtil.ItemCallback<CharacterDomain>(){
    override fun areItemsTheSame(oldItem: CharacterDomain, newItem: CharacterDomain): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharacterDomain, newItem: CharacterDomain): Boolean {
        return oldItem == newItem
    }

}

