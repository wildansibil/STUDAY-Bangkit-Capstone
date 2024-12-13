package com.studay.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.studay.app.R
import com.studay.app.api.dataclass.Angka

class MenghitungAdapter(
    private val formulaList: List<Angka>,
    private val onItemClick: (Angka) -> Unit
) : RecyclerView.Adapter<MenghitungAdapter.FormulaViewHolder>() {

    inner class FormulaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAngka: TextView = itemView.findViewById(R.id.tvAngka)
        val ivSound: ImageView = itemView.findViewById(R.id.ivSound)

        fun bind(angka: Angka) {
            tvAngka.text = angka.formula // Menampilkan formula (misalnya "1 + 1 = 2")

            // Cek apakah ada suara (Anda bisa menambahkan logika jika ingin memanfaatkan ivSound)
            ivSound.visibility = View.VISIBLE // Anda bisa menambahkan logika jika ingin menampilkan suara tertentu

            itemView.setOnClickListener { onItemClick(angka) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormulaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_angka, parent, false) // Menggunakan layout item_angka
        return FormulaViewHolder(view)
    }

    override fun onBindViewHolder(holder: FormulaViewHolder, position: Int) {
        holder.bind(formulaList[position])
    }

    override fun getItemCount() = formulaList.size
}