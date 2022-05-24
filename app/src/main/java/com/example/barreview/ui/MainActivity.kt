package com.example.barreview.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.barreview.data.auth.AuthDatasource
import com.example.barreview.data.bar.BarDatasource
import com.example.barreview.databinding.ActivityMainBinding
import com.example.barreview.domain.auth.AuthRepo
import com.example.barreview.domain.bar.BarRepo
import com.example.barreview.util.Resource
import com.example.barreview.viewmodel.auth.AuthViewModel
import com.example.barreview.viewmodel.auth.AuthViewModelFactory
import com.example.barreview.viewmodel.bar.BarViewModel
import com.example.barreview.viewmodel.bar.BarViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding;
    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(AuthRepo(
        AuthDatasource()
    )) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener{
        singIn(it.context,binding.userTF.text.toString(),binding.passTF.text.toString())
        }
    }

    private fun singIn(context: Context,email : String,password : String) {
        if (!checker(email, password)) {
            viewModel.signIn(email, password).observe(this,Observer {
                when (it) {

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        val intent = Intent(context, ContainerActivity::class.java)
                        context.startActivity(intent)
                        Toast.makeText(this,"Autenticacion correcta",Toast.LENGTH_SHORT).show()
                    }

                    is Resource.Failure -> {
                        Toast.makeText(this, it.exception.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun checker(email : String, password: String): Boolean {
        var error = false
        binding.userTFL.error = null
        binding.passTFL.error = null
        if (email == "") {
            binding.userTFL.error = "Error"
            error = true
        }
        if (password == "") {
            binding.passTFL.error = "Error"
            error = true
        }
        return error
    }


}