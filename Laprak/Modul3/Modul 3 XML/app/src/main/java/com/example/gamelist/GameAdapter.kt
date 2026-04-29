package com.example.gamelist

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.gamelist.databinding.ItemGameBinding

class GameAdapter(
    private val list: List<Game>,
    private val context: Context
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

        holder.binding.imgGame.setImageResource(data.image)
        holder.binding.tvName.text = data.name
        holder.binding.tvYear.text = data.year
        holder.binding.tvDesc.text = data.desc
        holder.binding.tvGenre.text = data.genre

        holder.binding.btnOfficial.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
            context.startActivity(intent)
        }

        holder.binding.btnDetail.setOnClickListener {

            val fragment = DetailFragment()

            val bundle = Bundle().apply {
                putString("name", data.name)
                putString("desc", data.desc)
                putString("year", data.year)
                putString("genre", data.genre)
                putInt("img", data.image)
            }

            fragment.arguments = bundle

            (context as AppCompatActivity)
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}