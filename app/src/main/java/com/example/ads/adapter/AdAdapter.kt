package com.example.ads.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.ads.R
import com.example.ads.databinding.AdItemBinding
import com.example.ads.model.Ads

class AdAdapter(var items : List<Ads>) : RecyclerView.Adapter<AdAdapter.viewholder>() {


    class viewholder(var binding: AdItemBinding) :ViewHolder(binding.root){
        fun bind(data :Ads){
            Glide.with(binding.root.context).load(data.imgURL).placeholder(R.drawable.baseline_home_24).into(binding.adImage)
            binding.txtDesc.text = data.description
            binding.txtPrice.text = data.price.toString()
            Log.e( "bind12: ",data.description.toString() )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val binding = AdItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewholder(binding)
    }

    override fun getItemCount(): Int {
     return items.size

    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(items[position])
        Log.e( "getItemCount: ",itemCount.toString() )
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.binding.root.context, "showed", Toast.LENGTH_SHORT).show()
            Log.e( "onBindViewHolder: ","show" )
        }

    }
}