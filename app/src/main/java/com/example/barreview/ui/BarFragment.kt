package com.example.barreview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barreview.adapter.BeerAdapter
import com.example.barreview.data.bar.BarDatasource
import com.example.barreview.databinding.FragmentBarBinding
import com.example.barreview.domain.bar.BarRepo
import com.example.barreview.model.Bar
import com.example.barreview.model.FoodReview
import com.example.barreview.util.Dialogs
import com.example.barreview.util.Resource
import com.example.barreview.viewmodel.bar.BarViewModel
import com.example.barreview.viewmodel.bar.BarViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class BarFragment : Fragment() {

    companion object {
        val ID = "id"
        var changes = false
    }

    private var _binding: FragmentBarBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : BeerAdapter
    private val viewModel by viewModels<BarViewModel> {BarViewModelFactory(BarRepo(BarDatasource()))}
    private var barId : String? = null
    private lateinit var bar : Bar
    private var foodReview: FoodReview? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            barId = it.getString(ID)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBarBinding.inflate(inflater,container,false)
        val view = binding.root;
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = BeerAdapter(this)
        recyclerView.adapter = adapter
        
        observeBeerData(barId)
        observeBarData(barId)
        observeFoodData(barId)

        binding.addFoodBtn.setOnClickListener {
            val dialog = activity?.let { it1 -> Dialogs(it1) }
            if (dialog != null) {
                dialog.addFoodReviewDialog()
                dialog.setMyDialogListener(object : Dialogs.MyDialogListener {
                    override fun onSelectedAValue(value: Float) {
                        addFoodReview(barId,value)
                    }
                })
                dialog.show()
            }
        }

        binding.lastReviewIB.setOnClickListener{
            if (foodReview != null) {
                val sdf = SimpleDateFormat("dd/MM/yyyy",Locale.US)
                val date = foodReview!!.created_at?.let { it1 -> sdf.format(it1) }
                val dialog = activity?.let { it1 -> Dialogs(it1) }
                if (dialog != null) {
                    foodReview!!.rating?.let { it1 -> if (date != null) {
                        dialog.lastFoodReview(it1,date)
                    } }
                    dialog.show()
                }
            }
        }

        binding.addBeerBtn.setOnClickListener{
            val action = BarFragmentDirections.actionBarFragmentToNewBeerFragment(barId!!)
            view?.findNavController()?.navigate(action)
        }
    }

    private fun observeBeerData(id: String?) {
        if (id != null) {
            viewModel.fetchBeerList(id).observe(viewLifecycleOwner, Observer {
                when (it) {

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        adapter.beerList = it.data
                        var rating = 0f
                        var counter = 0
                        for (beerReview in it.data){
                            rating += beerReview.rating!!
                            counter += 1
                        }
                        val beerRating = rating/counter
                        binding.beerFB.rating = beerRating
                        adapter.notifyDataSetChanged()
                        if (changes){
                            updateBarRating()
                        }
                    }

                    is Resource.Failure -> {
                        Toast.makeText(activity, it.exception.message, Toast.LENGTH_SHORT).show()


                    }
                }
            })
        }
    }

    private fun updateBarRating() {
        val beerRating = binding.beerFB.rating
        val foodRating = binding.foodRB.rating
        val generalRating : Float
        if (beerRating == 0f) {
            generalRating = foodRating
        } else if (foodRating == 0f) {
            generalRating = beerRating
        } else {
            generalRating = (beerRating+foodRating)/2
        }
        barId?.let {
            viewModel.updateBeerRating(it,generalRating,foodRating,beerRating).observe(viewLifecycleOwner, Observer {
                when(it) {

                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        changes = false

                    }

                    is Resource.Failure -> {
                        Toast.makeText(activity, it.exception.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

    }

    private fun addFoodReview(id: String?, value: Float) {
        if (id != null) {
            viewModel.addFoodReview(id,value).observe(viewLifecycleOwner, Observer {
                when(it) {

                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        changes = true
                        observeFoodData(id)

                    }

                    is Resource.Failure -> {
                        Toast.makeText(activity, it.exception.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun observeFoodData(id: String?) {
        if (id != null) {
            viewModel.fetchFoodReviewList(id).observe(viewLifecycleOwner, Observer {
                when (it) {

                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        var rating = 0f
                        var counter = 0
                        for (foodReview in it.data){
                            rating += foodReview.rating!!
                            counter += 1
                        }
                        binding.foodRB.rating = rating/counter
                        foodReview = it.data[0]
                        if (changes){
                            updateBarRating()
                        }
                    }

                    is Resource.Failure -> {
                        Toast.makeText(activity, it.exception.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun observeBarData(id: String?) {
        if (id != null) {
            viewModel.getBar(id).observe(viewLifecycleOwner, Observer {
                when (it) {

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                        bar = it.data
                        setUI()
                    }

                    is Resource.Failure -> {
                        Toast.makeText(activity, it.exception.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    fun destroyBeer(id: String?) {
        barId?.let {
            if (id != null) {
                viewModel.deleteBeer(it,id).observe(viewLifecycleOwner,Observer{
                    when (it) {

                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            observeBeerData(barId)
                            changes = true
                            adapter.notifyDataSetChanged()
                        }

                        is Resource.Failure -> {
                            Toast.makeText(activity, it.exception.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }

    }

    private fun setUI() {
        binding.barNameTV.setText(bar.name)
        binding.barAddressTV.setText(bar.address)
        binding.barNeighborhoodTV.setText(bar.neighborhood)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}