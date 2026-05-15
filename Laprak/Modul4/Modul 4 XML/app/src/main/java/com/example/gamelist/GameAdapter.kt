package com.example.gamelist

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamelist.databinding.ItemGameBinding
import timber.log.Timber

class GameAdapter(
    private val list: List<Game>,
    private val context: Context,
    private val onDetailClick: (Game) -> Unit
) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemGameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]

        Timber.d("Data game masuk ke list: ${data.name}")

        holder.binding.imgGame.setImageResource(data.image)
        holder.binding.tvName.text = data.name
        holder.binding.tvYear.text = data.year
        holder.binding.tvDesc.text = data.desc
        holder.binding.tvGenre.text = data.genre

        holder.binding.btnOfficial.setOnClickListener {

            Timber.d("Tombol Official ditekan: ${data.name}")

            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(data.url))

            context.startActivity(intent)
        }

        holder.binding.btnDetail.setOnClickListener {

            Timber.d("Tombol Detail ditekan: ${data.name}")

            Timber.d("Berpindah ke halaman detail game: ${data.name}")

            onDetailClick(data)
        }
    }
}