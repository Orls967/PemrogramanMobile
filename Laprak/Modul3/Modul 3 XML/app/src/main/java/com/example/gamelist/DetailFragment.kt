package com.example.gamelist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.gamelist.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val name = arguments?.getString("name")
        val desc = arguments?.getString("desc")
        val year = arguments?.getString("year")
        val genre = arguments?.getString("genre")
        val img = arguments?.getInt("img")

        binding.tvName.text = name
        binding.tvDesc.text = desc
        binding.tvYear.text = "Tahun: $year"
        binding.tvGenre.text = "Genre: $genre"

        if (img != null) {
            binding.imgDetail.setImageResource(img)
        }

        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}