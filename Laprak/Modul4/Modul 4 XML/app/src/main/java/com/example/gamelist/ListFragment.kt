package com.example.gamelist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamelist.databinding.FragmentListBinding
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    private val viewModel: GameViewModel by viewModels {
        GameViewModelFactory("Game List Application")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentListBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        lifecycleScope.launch {

            viewModel.listGame.collect { list ->

                binding.recyclerView.adapter =
                    GameAdapter(
                        list,
                        requireActivity()
                    ) { game ->

                        viewModel.selectGame(game)

                        val fragment = DetailFragment()

                        val bundle = Bundle().apply {
                            putString("name", game.name)
                            putString("desc", game.desc)
                            putString("year", game.year)
                            putString("genre", game.genre)
                            putInt("img", game.image)
                        }

                        fragment.arguments = bundle

                        requireActivity()
                            .supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, fragment)
                            .addToBackStack(null)
                            .commit()
                    }
            }
        }

        return binding.root
    }
}