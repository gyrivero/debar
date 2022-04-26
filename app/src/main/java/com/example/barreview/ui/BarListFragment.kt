package com.example.barreview.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barreview.R
import com.example.barreview.adapter.BarAdapter
import com.example.barreview.data.Datasource
import com.example.barreview.databinding.FragmentBarListBinding
import com.example.barreview.model.Bar
import com.google.firebase.database.*


class BarListFragment : Fragment() {

    private var _binding: FragmentBarListBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var barList = mutableListOf<Bar>()
    private lateinit var adapter : BarAdapter
    private lateinit var database: DatabaseReference


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
        database = FirebaseDatabase.getInstance().reference;

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        //barList = createBar()
        barList = Datasource.createDataSet()
        adapter = BarAdapter(barList,this)
        recyclerView.adapter = adapter
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
                database = FirebaseDatabase.getInstance().reference;
                // write from the database
                val bar :Bar = Bar(2,"Blaadasdasdadsadasdsadasdr","Cordoba","Palermo",3.0f)
                database.child("Bar").push().setValue(bar)
                //barList.add(bar)
                if (!barList.contains(bar)){
                    barList.add(bar)
                }
                recyclerView.adapter?.notifyDataSetChanged()

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}