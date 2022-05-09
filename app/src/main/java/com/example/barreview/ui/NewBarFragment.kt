package com.example.barreview.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.barreview.data.newbar.NewBarDatasource
import com.example.barreview.databinding.FragmentNewBarBinding
import com.example.barreview.domain.newbar.NewBarRepo
import com.example.barreview.util.Resource
import com.example.barreview.viewmodel.barlist.BarListViewModelFactory
import com.example.barreview.viewmodel.barlist.BarListViewModel
import com.example.barreview.viewmodel.newbar.NewBarViewModel
import com.example.barreview.viewmodel.newbar.NewBarViewModelFactory

class NewBarFragment : Fragment() {
    private var _binding: FragmentNewBarBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels <NewBarViewModel> { NewBarViewModelFactory(NewBarRepo(NewBarDatasource())) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNewBarBinding.inflate(inflater,container,false)
        val view = binding.root;
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createBtn.setOnClickListener{
            createBar(binding.nameBarTF.text.toString(),binding.addressBarTF.text.toString(),binding.neigborhoodTF.text.toString())
        }

    }

    private fun createBar(name : String, address: String, neighborhood: String) {
        if (!checker(name, address, neighborhood)) {
            viewModel.addBar(name,address,neighborhood).observe(viewLifecycleOwner, Observer {
                when (it) {

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                        val action = NewBarFragmentDirections.actionNewBarFragmentToBarListFragment()
                        view?.findNavController()?.navigate(action)

                    }

                    is Resource.Failure -> {
                        Toast.makeText(activity, it.exception.message, Toast.LENGTH_SHORT).show()


                    }
                }
            })
        }

    }


    private fun checker(name : String, address: String, neighborhood: String): Boolean {
        var error = false
        binding.nameBarTFL.error = null
        binding.addressBarTFL.error = null
        binding.neigborhoodTFL.error = null
        if (name == "") {
            binding.nameBarTFL.error = "Error"
            error = true
        }
        if (address == "") {
            binding.addressBarTFL.error = "Error"
            error = true
        }
        if (neighborhood == "") {
            binding.neigborhoodTFL.error = "Error"
            error = true
        }
        return error
    }

}