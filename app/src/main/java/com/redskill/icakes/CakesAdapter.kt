package com.redskill.icakes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.redskill.icakes.databinding.ItemCakeBinding
import com.redskill.icakes.model.Cake
import com.squareup.picasso.Picasso

class CakesAdapter : ListAdapter<Cake, CakesAdapter.ViewHolder>(DiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCakeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val itemCakeBinding: ItemCakeBinding) :
        RecyclerView.ViewHolder(itemCakeBinding.root) {
        fun bind(cake: Cake) {
            itemCakeBinding.cakeTitle.text = cake.title
            Picasso.get().load(cake.image).into(itemCakeBinding.cakeImage)
        }
    }
}

class DiffUtils : DiffUtil.ItemCallback<Cake>() {
    override fun areItemsTheSame(oldItem: Cake, newItem: Cake): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Cake, newItem: Cake): Boolean {
        return oldItem == newItem
    }
}