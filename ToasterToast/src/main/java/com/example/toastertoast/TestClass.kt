//package com.example.toastertoast
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//
//class TestClass : AppCompatActivity (){
//        private lateinit var binding : ActivityMainBinding
//
//        override fun onCreate(savedInstanceState : Bundle?) {
//                super.onCreate(savedInstanceState)
//                binding = ActivityMainBinding.inflate(layoutInflater)
//                setContentView(binding.root)
//
//                binding.button.setOnClickListener {
//                        Toaster.Toast(this@MainActivity,"hello")
//
//                        startActivity(Intent(this@MainActivity, TestClass::class.java))
//                }
//        }
//
//
//}