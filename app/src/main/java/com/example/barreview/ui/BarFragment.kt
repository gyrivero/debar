package com.example.barreview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.barreview.databinding.FragmentBarBinding

class BarFragment : Fragment() {

    companion object {
        val NAME = "name"
    }

    private var _binding: FragmentBarBinding? = null
    private val binding get() = _binding!!

    private var barName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            barName = it.getString(NAME)
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

        //Provisorio - Falta implementar base de datos
        binding.barNameTV.setText(barName.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}