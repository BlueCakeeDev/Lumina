package com.example.lumina.ui.ItemsRecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lumina.R
import com.example.lumina.models.Pelicula

class ItemVertical(private val movies: List<Pelicula>) :
    RecyclerView.Adapter<ItemVertical.MovieViewHolder>() {

    private var onItemClickListener: ((Pelicula) -> Unit)? = null

    fun setOnItemClickListener(listener: (Pelicula) -> Unit) {
        onItemClickListener = listener
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.txv_TituloPelicula)
        val imageView: ImageView = itemView.findViewById(R.id.img_Pelicula)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_carrucel, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movies[position]

        holder.titleTextView.text = currentItem.title
        holder.imageView.setImageResource(currentItem.imageResId)
        holder.ratingBar.rating = currentItem.rating

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(currentItem)
        }
    }

    override fun getItemCount() = movies.size
}
