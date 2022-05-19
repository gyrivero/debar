package com.example.barreview.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.barreview.R
import com.example.barreview.data.newbeer.NewBeerDatasource
import com.example.barreview.databinding.FragmentNewBeerBinding
import com.example.barreview.domain.newbeer.NewBeerRepo
import com.example.barreview.util.Resource
import com.example.barreview.viewmodel.newbeer.NewBeerViewModel
import com.example.barreview.viewmodel.newbeer.NewBeerViewModelFactory


class NewBeerFragment : Fragment() {
    companion object {
        val ID = "id"
    }

    private var _binding: FragmentNewBeerBinding? = null
    private val binding get() = _binding!!
    private var barId : String? = null
    private val viewModel by viewModels <NewBeerViewModel> { NewBeerViewModelFactory(
        NewBeerRepo(
            NewBeerDatasource()
        )
    ) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            barId = it.getString(ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewBeerBinding.inflate(inflater,container,false)
        val view = binding.root;
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createBtn.setOnClickListener{
            createBeer(binding.beerColorRG.checkedRadioButtonId, binding.nameBeerTF.text.toString(),binding.brandBeerTF.text.toString(),binding.newBeerRB.rating)
        }
    }

    private fun createBeer(idRB: Int, name: String, brand: String, rating: Float) {
        if (!checker(idRB,name, brand,rating)) {
            barId?.let {
                viewModel.addBeer(it,defineColor(idRB),name,brand,rating).observe(viewLifecycleOwner,
                    Observer {
                        when (it) {

                            is Resource.Loading -> {

                            }

                            is Resource.Success -> {
                                BarFragment.changes = true
                                val id : String = barId as String
                                val action =
                                NewBeerFragmentDirections.actionNewBeerFragmentToBarFragment(id)
                                view?.findNavController()?.navigate(action)


                            }

                            is Resource.Failure -> {
                                Toast.makeText(activity, it.exception.message, Toast.LENGTH_SHORT)
                                    .show()


                            }
                        }
                    })
            }
        }

    }

    private fun defineColor(idRB: Int) : String{
        if (idRB == binding.blondRB.id) {
            return "#" + resources.getString(0+R.color.blonde_beer).substring(3)
        } else if (idRB == binding.blackRB.id) {
            return "#" + resources.getString(0+R.color.black_beer).substring(3)
        } else if (idRB == binding.redRB.id) {
            return "#" + resources.getString(0+R.color.red_beer).substring(3)
        } else {
            return "#" + resources.getString(0+R.color.blonde_beer).substring(3)
        }

    }


    private fun checker(color: Int, name: String, brand: String, rating: Float): Boolean {
        var error = false
        binding.nameBeerTFL.error = null
        binding.brandBeerTFL.error = null
        binding.colorLabelTV.error = null
        binding.ratingBarError.error = null
        if (name == "") {
            binding.nameBeerTFL.error = "Error"
            error = true
        }
        if (brand == "") {
            binding.brandBeerTFL.error = "Error"
            error = true
        }
        if (color == -1) {
            binding.colorLabelTV.error = "Error"
            error = true
        }
        if (rating == 0f) {
            binding.ratingBarError.error ="Error"
            error = true
        }
        return error
    }
}