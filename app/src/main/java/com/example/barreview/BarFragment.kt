package com.example.barreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.barreview.databinding.FragmentBarBinding

class BarFragment : Fragment() {

    companion object {
        val ID = "id"
    }

    private var _binding: FragmentBarBinding? = null
    private val binding get() = _binding!!

    private var barId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            barId = it.getInt(ID)
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
        binding.barNameTV.setText(barId.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}