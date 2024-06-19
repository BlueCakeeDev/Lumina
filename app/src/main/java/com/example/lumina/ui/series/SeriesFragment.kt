package com.example.lumina.ui.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lumina.databinding.FragmentSeriesBinding
import com.example.lumina.models.MovieRepository
import com.example.lumina.ui.ItemsRecyclerView.ItemHorizontal

class SeriesFragment : Fragment() {

    private var _binding: FragmentSeriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeriesBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val allMovies = MovieRepository.getMovies()
        val seriesMovies = allMovies.filter { it.tipo == 1 }

        val recyclerView: RecyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = ItemHorizontal(seriesMovies)
        recyclerView.adapter = adapter

        // Agregar evento de clic al adaptador
        adapter.setOnItemClickListener { movie ->
            val id = movie.id
            val action = SeriesFragmentDirections.actionNavSeriesToArticulo(id)
            findNavController().navigate(action)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
