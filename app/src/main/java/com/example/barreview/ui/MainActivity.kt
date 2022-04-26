package com.example.barreview.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.barreview.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding;
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.loginBtn.setOnClickListener{
        singIn(it.context,binding.userTF.text.toString(),binding.passTF.text.toString())
        }
    }

    fun singIn(context: Context,email : String,password : String) {
        if (!checker(email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val intent = Intent(context, ContainerActivity::class.java)
                        context.startActivity(intent)
                        Toast.makeText(baseContext,"Autenticacion correcta " + user,Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun checker(email : String, password: String): Boolean {
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