package com.example.gamelist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamelist.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentListBinding.inflate(inflater, container, false)

        val list = listOf(

            Game(
                "Mobile Legends",
                "2016",
                "Mobile Legends adalah game MOBA 5v5 yang sangat populer di perangkat mobile. Dalam permainan ini, pemain bekerja sama dalam tim untuk menghancurkan base lawan dengan strategi, koordinasi, dan pemilihan hero yang tepat. Setiap pemain memiliki peran seperti tank, marksman, mage, dan assassin yang harus dimainkan secara optimal.",
                "MOBA",
                R.drawable.ml,
                "https://www.mobilelegends.com"
            ),

            Game(
                "Genshin Impact",
                "2020",
                "Genshin Impact adalah game RPG open-world dengan grafis bergaya anime yang sangat detail dan indah. Pemain dapat menjelajahi dunia Teyvat yang luas, menyelesaikan berbagai quest, serta bertarung menggunakan sistem elemen yang unik. Game ini juga memiliki sistem gacha untuk mendapatkan karakter dan senjata.",
                "RPG",
                R.drawable.genshin,
                "https://genshin.hoyoverse.com"
            ),

            Game(
                "Resident Evil 9",
                "2025",
                "Resident Evil 9 menghadirkan pengalaman survival horror dengan atmosfer yang mencekam dan penuh ketegangan. Game ini menampilkan cerita yang gelap, lingkungan yang detail, serta gameplay yang menantang di mana pemain harus bertahan hidup dari ancaman zombie dan makhluk berbahaya lainnya.",
                "Horror",
                R.drawable.requiem,
                "https://www.capcom.com"
            ),

            Game(
                "PUBG Mobile",
                "2018",
                "PUBG Mobile adalah game battle royale di mana hingga 100 pemain bertarung dalam satu peta untuk menjadi yang terakhir bertahan hidup. Pemain harus mencari senjata, mengatur strategi, dan memanfaatkan zona aman yang terus mengecil untuk memenangkan pertandingan.",
                "Battle Royale",
                R.drawable.pubg,
                "https://www.pubgmobile.com"
            ),

            Game(
                "Valorant",
                "2020",
                "Valorant adalah game FPS taktis berbasis tim yang menggabungkan mekanik shooter klasik dengan kemampuan unik setiap karakter. Pemain harus bekerja sama dalam tim untuk menyerang atau bertahan, menggunakan strategi, komunikasi, dan akurasi tembakan yang tinggi.",
                "FPS",
                R.drawable.valorant,
                "https://playvalorant.com"
            ),

            Game(
                "Dota 2",
                "2013",
                "Dota 2 adalah game MOBA kompetitif di platform PC yang dikenal dengan kompleksitas gameplay dan strategi mendalam. Setiap tim terdiri dari lima pemain yang harus bekerja sama untuk menghancurkan Ancient lawan, dengan berbagai hero yang memiliki kemampuan unik.",
                "MOBA",
                R.drawable.dota2,
                "https://www.dota2.com"
            ),

            Game(
                "Elden Ring",
                "2022",
                "Elden Ring adalah game RPG open-world bergaya souls-like yang menawarkan dunia luas dengan kebebasan eksplorasi tinggi. Game ini dikenal dengan tingkat kesulitan yang menantang, desain dunia yang detail, serta sistem pertarungan yang kompleks.",
                "RPG",
                R.drawable.eldenring,
                "https://en.bandainamcoent.eu/elden-ring"
            ),

            Game(
                "Gran Turismo 7",
                "2022",
                "Gran Turismo 7 adalah game simulasi balap realistis yang menghadirkan pengalaman berkendara autentik. Game ini menawarkan berbagai jenis mobil, lintasan balap, serta sistem fisika yang mendekati dunia nyata.",
                "Racing",
                R.drawable.granturismo7,
                "https://www.gran-turismo.com"
            ),

            Game(
                "GTA V",
                "2013",
                "GTA V adalah game open-world yang memberikan kebebasan kepada pemain untuk menjelajahi kota Los Santos. Pemain dapat menjalankan berbagai misi, melakukan aktivitas bebas, serta mengikuti cerita yang melibatkan tiga karakter utama dengan alur yang kompleks.",
                "Action",
                R.drawable.gtav,
                "https://www.rockstargames.com/gta-v"
            ),

            Game(
                "Survivor.io",
                "2022",
                "Survivor.io adalah game survival casual di mana pemain harus bertahan dari serangan zombie yang terus berdatangan. Pemain dapat meng-upgrade senjata, memilih skill, dan bertahan selama mungkin dalam kondisi yang semakin sulit.",
                "Casual",
                R.drawable.survivorio,
                "https://store.habby.com/game/3"
            )
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = GameAdapter(list, requireActivity())

        return binding.root
    }
}