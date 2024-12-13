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

class MembacaAdapter(
    private val hurufList: List<Huruf>,
    private val onItemClick: (Huruf) -> Unit
) : RecyclerView.Adapter<MembacaAdapter.HurufViewHolder>() {

    inner class HurufViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHuruf: TextView = itemView.findViewById(R.id.tvHuruf)
        val ivSound: ImageView = itemView.findViewById(R.id.ivSound)

        fun bind(huruf: Huruf) {
            tvHuruf.text = huruf.huruf.toString() // Menampilkan huruf atau suku kata

            // Cek apakah ada URL suara, jika tidak, sembunyikan gambar suara
            ivSound.visibility = View.VISIBLE
            itemView.setOnClickListener { onItemClick(huruf) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HurufViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_huruf, parent, false)
        return HurufViewHolder(view)
    }

    override fun onBindViewHolder(holder: HurufViewHolder, position: Int) {
        holder.bind(hurufList[position])
    }

    override fun getItemCount() = hurufList.size
}
