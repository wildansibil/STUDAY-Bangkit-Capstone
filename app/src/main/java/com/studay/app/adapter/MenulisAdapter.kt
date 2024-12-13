package com.studay.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.studay.app.R
import com.studay.app.api.dataclass.Huruf
import com.studay.app.databinding.ItemHurufBinding

class MenulisAdapter(
    private val hurufList: List<Huruf>,
    private val onItemClick: (Huruf) -> Unit
) : RecyclerView.Adapter<MenulisAdapter.HurufViewHolder>() {

    inner class HurufViewHolder(private val binding: ItemHurufBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(huruf: Huruf) {
            // Set the letter text
            binding.tvHuruf.text = huruf.huruf.toString()

            // Load sound image using Glide if URL is not null
            huruf.suara?.let {
                Glide.with(itemView.context)
                    .load(it)
                    .placeholder(R.drawable.ic_sound) // Placeholder in case of failure
                    .into(binding.ivSound)
            }

            // Handle item click
            itemView.setOnClickListener { onItemClick(huruf) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HurufViewHolder {
        val binding = ItemHurufBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HurufViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HurufViewHolder, position: Int) {
        holder.bind(hurufList[position])
    }

    override fun getItemCount() = hurufList.size
}