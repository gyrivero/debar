package com.example.barreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barreview.adapter.BarAdapter
import com.example.barreview.databinding.FragmentBarListBinding
import com.example.barreview.model.Bar

private var _binding: FragmentBarListBinding? = null
private val binding get() = _binding!!
private lateinit var recyclerView: RecyclerView
private var isLinearLayoutManager = true
private lateinit var barList: MutableList<Bar>

class BarListFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBarListBinding.inflate(inflater,container,false)
        val view = binding.root;
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        barList = createBar()
        recyclerView = binding.recyclerView
        chooseLayout()
    }

    private fun createBar(): MutableList<Bar> {
        val bar :Bar = Bar(1,"Blest","Cordobna","Palermo",3.0f)
        return mutableListOf(bar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun chooseLayout() {
        when (isLinearLayoutManager) {
            true -> {
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = BarAdapter(barList)
            }
            false -> {
                recyclerView.layoutManager = GridLayoutManager(context, 4)
                recyclerView.adapter = BarAdapter(barList)
            }
        }
    }
}