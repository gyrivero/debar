package com.example.barreview

import android.R.attr.data
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barreview.adapter.BarAdapter
import com.example.barreview.databinding.FragmentBarListBinding
import com.example.barreview.model.Bar


class BarListFragment : Fragment() {

    companion object {
        var checked : Boolean = false;
    }

    private var _binding: FragmentBarListBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var isLinearLayoutManager = true
    private lateinit var barList: MutableList<Bar>
    private lateinit var adapter : BarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

    //Provisorio - Falta implementar base de datos
    private fun createBar(): MutableList<Bar> {
        val bar :Bar = Bar(1,"Blest TARARARA TARARARARARA","Cordoba","Palermo",3.0f)
        return mutableListOf(bar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun chooseLayout() {
        adapter = BarAdapter(barList,this)
        when (isLinearLayoutManager) {
            true -> {
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = adapter
            }
            false -> {
                recyclerView.layoutManager = GridLayoutManager(context, 4)
                recyclerView.adapter = adapter
            }
        }
    }

    fun destroyBar(index:Int){
        Toast.makeText(activity,barList[index].name, Toast.LENGTH_SHORT).show()
        barList.removeAt(index)
        adapter.notifyItemRemoved(index)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_bar -> {
                val bar :Bar = Bar(1,"Blest TARARARA TARARARARARA","Cordoba","Palermo",3.0f)
                barList.add(bar)
                adapter.notifyItemInserted(barList.size-1)

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}