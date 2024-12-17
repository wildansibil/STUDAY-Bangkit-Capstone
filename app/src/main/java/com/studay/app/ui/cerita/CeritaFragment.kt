package com.studay.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studay.app.adapter.CeritaAdapter
import com.studay.app.api.ApiService
import com.studay.app.api.RetrofitClient
import com.studay.app.api.dataclass.Cerita
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CeritaFragment : Fragment() {

    private lateinit var rvCerita: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val stories = listOf(
        Cerita("Kisah Lala si Kelinci Pemimpi",
            "Di sebuah hutan kecil yang damai, hiduplah seekor kelinci kecil bernama Lala. Lala suka sekali melihat langit. Setiap malam, ia duduk di bawah pohon besar sambil memandang bintang-bintang yang bersinar terang. “Aku ingin ke sana, ke bintang itu!” katanya sambil menunjuk langit.Teman-temannya, seperti Riri si Rusa dan Timi si Tupai, tertawa kecil. “Lala, kelinci tidak bisa ke bintang. Itu terlalu jauh!” kata Riri. Tapi Lala tidak peduli. Setiap hari, ia memikirkan cara bagaimana bisa mencapai bintang. Ia mencoba melompat setinggi-tingginya, tetapi tetap saja bintang itu jauh. Lalu, ia membuat tangga dari ranting-ranting, tetapi tangganya terlalu pendek. Meski begitu, Lala tidak mau menyerah. Suatu hari, Timi si Tupai datang sambil membawa banyak balon warna-warni. “Lala, ini untukmu! Aku mendengar mimpimu, jadi aku pikir balon-balon ini bisa membantumu terbang\".“Wah, terima kasih, Timi!” seru Lala senang. Ia mengikat balon-balon itu ke tubuhnya, dan perlahan-lahan, Lala mulai terangkat ke udara. “Lihat aku! Aku terbang!” teriak Lala sambil melambai ke teman-temannya di bawah. Semua binatang di hutan melompat kegirangan melihat Lala melayang di langit. Lala tidak sampai ke bintang, tapi ia bisa melihat bintang-bintang lebih dekat dari sebelumnya. “Ternyata mimpiku benar-benar bisa terjadi!” katanya sambil tersenyum. Ketika akhirnya balon-balon membawa Lala kembali ke tanah, semua temannya menyambutnya dengan pelukan. Mereka belajar satu hal dari Lala: “Tidak apa-apa bermimpi besar, asalkan kita tidak pernah menyerah.” Sejak saat itu, semua binatang di hutan juga berani bermimpi, sama seperti Lala si kelinci kecil.",
            "https://assets-a1.kompasiana.com/items/album/2021/01/06/ilustrasi-kelinci-5ff585908ede48761f7b2cd4.jpg?t=o&v=1200"),

        Cerita("Si Kucing dan Tali Ajaib",
            "Di sebuah desa kecil, hiduplah seekor kucing bernama Momo. Momo sangat suka bermain, terutama dengan bola benang miliknya. Ia selalu berlari-lari mengejar benang itu, membuat teman-temannya tertawa melihat tingkah lucunya. Suatu hari, saat sedang bermain di dekat pohon besar, Momo melihat sebuah tali kecil yang menggantung dari cabang pohon. Tapi tali itu berbeda! Warnanya berkilauan seperti pelangi. “Wah, ini pasti tali ajaib!” pikir Momo dengan mata berbinar. Tanpa berpikir panjang, ia mulai menarik tali itu. Tapi anehnya, tali itu terus memanjang ke atas, seolah-olah tak ada ujungnya. “Ke mana ya ujung tali ini?” gumam Momo penasaran. Ia memutuskan untuk memanjat pohon dan mengikuti tali itu. Momo memanjat dan memanjat, sampai ia tiba di atas awan. Betapa terkejutnya ia melihat dunia yang penuh warna! Ada rumput yang empuk seperti bantal, bunga-bunga raksasa, dan kupu-kupu yang sebesar burung. Tiba-tiba, seekor burung kecil yang bercahaya mendekati Momo. “Halo, Momo! Kamu telah menemukan Tali Ajaib. Semua yang berani memanjatnya akan sampai di Dunia Awan ini,” kata burung itu sambil tersenyum. “Wah, aku senang sekali bisa ke sini! Tapi, aku ingin tahu, apa aku boleh membawa teman-temanku ke sini juga?” tanya Momo. “Tentu saja, tapi dengan satu syarat. Kamu harus berbagi ceritamu dan mengajarkan mereka untuk percaya pada keberanian mereka sendiri,” jawab burung kecil itu. Momo kembali ke desa dengan hati yang penuh semangat. Ia menceritakan petualangannya kepada teman-temannya, dan bersama-sama mereka memanjat tali ajaib itu. Semua binatang di desa akhirnya bisa menikmati keindahan Dunia Awan karena keberanian dan kebaikan Momo. Sejak hari itu, Momo selalu mengingatkan teman-temannya, “Jika kita punya keberanian dan hati yang baik, keajaiban akan selalu datang!”",
            "https://i.ytimg.com/vi/seV3I-5ty4w/maxresdefault.jpg"),

        Cerita("Riko dan Bintang yang Jatuh",
            "Di sebuah desa kecil yang sunyi, hiduplah seorang anak bernama Riko. Riko suka memandangi langit malam. Ia selalu kagum melihat bintang-bintang yang berkelap-kelip di atas sana. “Kalau aku punya bintang sendiri, aku akan menjadikannya teman!” kata Riko setiap malam. Suatu malam, saat ia sedang duduk di halaman rumahnya, tiba-tiba sebuah cahaya melesat di langit. Itu adalah bintang jatuh! Cahaya itu jatuh di hutan kecil di dekat rumah Riko. “Wow! Aku harus melihatnya!” seru Riko. Dengan penuh semangat, ia berlari ke arah hutan, membawa lentera kecil miliknya. Sesampainya di hutan, Riko melihat sesuatu yang luar biasa. Di tengah-tengah rerumputan, ada bintang kecil yang bersinar lembut. Tapi, bintang itu terlihat sedih. “Halo, bintang kecil. Kenapa kamu sedih?” tanya Riko. “Aku terjatuh dari langit. Aku tidak tahu bagaimana cara kembali ke sana,” jawab bintang kecil dengan suara lemah. Riko berpikir sejenak. “Jangan khawatir! Aku akan membantumu kembali ke langit!” Riko mengambil layang-layang besarnya, mengikat bintang kecil itu dengan hati-hati, dan menunggu angin yang kuat. Dengan penuh semangat, ia berlari, menerbangkan layang-layangnya setinggi mungkin. “Ayo, bintang kecil! Terbanglah kembali ke tempatmu!” teriak Riko. Tiba-tiba, angin besar datang dan membawa layang-layang itu tinggi ke langit. Bintang kecil itu perlahan-lahan naik, semakin tinggi, hingga akhirnya kembali bersinar di langit malam. “Terima kasih, Riko! Kamu adalah teman terbaikku!” teriak bintang kecil dari langit. Sejak saat itu, Riko selalu merasa ada bintang yang bersinar lebih terang di atas rumahnya. Ia tahu, itu adalah bintang kecil yang pernah ia tolong.",
            "https://cdns.klimg.com/bola.net/resized/810x540/library/upload/21/2023/09/996x664/riko-simanjuntak_600abe1.jpg")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cerita, container, false)
        rvCerita = view.findViewById(R.id.rvCerita)
        progressBar = view.findViewById(R.id.loadingCerita)

        setupRecyclerView()
        displayCeritaData() // Gunakan data manual di sini

        return view
    }

    private fun setupRecyclerView() {
        rvCerita.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun displayCeritaData() {
        progressBar.visibility = View.GONE
        rvCerita.adapter = CeritaAdapter(stories) { cerita ->
            val intent = Intent(requireContext(), DetailCeritaActivity::class.java).apply {
                putExtra("judul", cerita.judul)
                putExtra("deskripsi", cerita.deskripsi)
                putExtra("gambar", cerita.gambar)
            }
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
