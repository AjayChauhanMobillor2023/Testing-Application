package com.example.testapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testapplication.databinding.ActivityMainBinding
import com.example.toastertoast.Toaster


class MainActivity : AppCompatActivity() {

        private lateinit var binding : ActivityMainBinding

        override fun onCreate(savedInstanceState : Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityMainBinding.inflate(layoutInflater)
                setContentView(binding.root)

                binding.button.setOnClickListener {

//                        Toaster.Toast(this@MainActivity,"hello")

                        Toaster.showCustomToast(binding.rootView,"Hello Ajay")

                }
        }
}