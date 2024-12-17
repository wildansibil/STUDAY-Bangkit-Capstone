package com.studay.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.studay.app.api.dataclass.Cerita
import com.bumptech.glide.Glide
import com.studay.app.R

class CeritaAdapter(
    private val ceritaList: List<Cerita>,
    private val onItemClick: (Cerita) -> Unit
) : RecyclerView.Adapter<CeritaAdapter.CeritaViewHolder>() {

    inner class CeritaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvJudul: TextView = itemView.findViewById(R.id.tvJudul)
        val tvDeskripsi: TextView = itemView.findViewById(R.id.tvDeskripsi)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(cerita: Cerita) {
            tvJudul.text = cerita.judul

            // Menampilkan deskripsi singkat
            val shortDeskripsi = if (cerita.deskripsi.length > 100) {
                "${cerita.deskripsi.substring(0, 100)}..."
            } else {
                cerita.deskripsi
            }
            tvDeskripsi.text = shortDeskripsi

            // Cek apakah gambar adalah URL atau resource ID
            if (cerita.gambar.startsWith("http")) {
                Glide.with(itemView.context)
                    .load(cerita.gambar) // URL gambar
                    .into(imageView)
            } else {
                imageView.setImageResource(R.drawable.ic_image) // Gambar default
            }

            itemView.setOnClickListener { onItemClick(cerita) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CeritaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cerita, parent, false)
        return CeritaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CeritaViewHolder, position: Int) {
        holder.bind(ceritaList[position])
    }

    override fun getItemCount() = ceritaList.size
}