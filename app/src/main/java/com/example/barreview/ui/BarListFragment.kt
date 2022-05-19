package com.example.barreview.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barreview.R
import com.example.barreview.adapter.BarAdapter
import com.example.barreview.data.barlist.BarListDatasource
import com.example.barreview.databinding.FragmentBarListBinding
import com.example.barreview.domain.barlist.BarListRepo
import com.example.barreview.model.Bar
import com.example.barreview.viewmodel.barlist.BarListViewModel
import com.example.barreview.viewmodel.barlist.BarListViewModelFactory
import com.example.barreview.util.Resource


class BarListFragment : Fragment() {

    private var _binding: FragmentBarListBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : BarAdapter
    private val viewModel by viewModels <BarListViewModel> { BarListViewModelFactory(BarListRepo(BarListDatasource())) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun observeData() {
        viewModel.fetchBarList().observe(viewLifecycleOwner, Observer {
            when (it) {

                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    adapter.barList = it.data
                    adapter.notifyDataSetChanged()
                }
                is Resource.Failure -> {
                    Toast.makeText(activity, it.exception.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
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
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = BarAdapter(this)
        recyclerView.adapter = adapter


        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun destroyBar(index:Int){
        adapter.getbarList()[index].id?.let {
            viewModel.deleteBar(it).observe(viewLifecycleOwner, Observer {
                when (it) {

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        observeData()
                        adapter.notifyDataSetChanged()
                    }

                    is Resource.Failure -> {
                        Toast.makeText(activity, it.exception.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_bar -> {
                val action = BarListFragmentDirections.actionBarListFragmentToNewBarFragment()
                view?.findNavController()?.navigate(action)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}