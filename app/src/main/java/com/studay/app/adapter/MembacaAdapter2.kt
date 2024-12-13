package com.studay.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.studay.app.R
import com.studay.app.api.dataclass.SukuKata

class MembacaAdapter2(
    private val sukukataList: List<SukuKata>,
    private val onItemClick: (SukuKata) -> Unit
) : RecyclerView.Adapter<MembacaAdapter2.HurufViewHolder>() {

    inner class HurufViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHuruf: TextView = itemView.findViewById(R.id.tvHuruf)
        val ivSound: ImageView = itemView.findViewById(R.id.ivSound)

        fun bind(huruf: SukuKata) {
            tvHuruf.text = huruf.sukukata.toString() // Menampilkan huruf atau suku kata

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
        holder.bind(sukukataList[position])
    }

    override fun getItemCount() = sukukataList.size
}
