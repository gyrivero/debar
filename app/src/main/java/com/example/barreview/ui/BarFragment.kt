package com.example.barreview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.barreview.data.bar.BarDatasource
import com.example.barreview.databinding.FragmentBarBinding
import com.example.barreview.domain.bar.BarRepo
import com.example.barreview.model.Bar
import com.example.barreview.util.Dialogs
import com.example.barreview.util.Resource
import com.example.barreview.viewmodel.bar.BarViewModel
import com.example.barreview.viewmodel.bar.BarViewModelFactory

class BarFragment : Fragment() {

    companion object {
        val ID = "id"
    }

    private var _binding: FragmentBarBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<BarViewModel> {BarViewModelFactory(BarRepo(BarDatasource()))}
    private var barId : String? = null
    private lateinit var bar : Bar

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
        super.onViewCreated(view, savedInstanceState)

        observeBarData(barId)
        observeFoodData(barId)

        binding.foodBtn.setOnClickListener {
            val dialog = activity?.let { it1 -> Dialogs(it1) }
            if (dialog != null) {
                dialog.foodDialog()
                dialog.setMyDialogListener(object : Dialogs.MyDialogListener {
                    override fun onSelectedAValue(value: Float) {
                        addFoodReview(barId,value)
                    }

                })
                dialog.show()
            }
        }
    }

    private fun addFoodReview(id: String?, value: Float) {
        if (id != null) {
            viewModel.addFoodReview(id,value).observe(viewLifecycleOwner, Observer {
                when(it) {

                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
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