package com.example.lumina.ui.peliculas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lumina.databinding.FragmentPeliculasBinding
import com.example.lumina.models.MovieRepository
import com.example.lumina.ui.ItemsRecyclerView.ItemHorizontal

class PeliculaFragment : Fragment() {

    private var _binding: FragmentPeliculasBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ItemHorizontal

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPeliculasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val allMovies = MovieRepository.getMovies()
        val filteredMovies = allMovies.filter { it.tipo == 0 }

        val recyclerView: RecyclerView = binding.recyclerView

        adapter = ItemHorizontal(filteredMovies)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.setOnItemClickListener { movie ->
            val id = movie.id
            val action = PeliculaFragmentDirections.actionNavPeliculasToArticulo(id)
            findNavController().navigate(action)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
