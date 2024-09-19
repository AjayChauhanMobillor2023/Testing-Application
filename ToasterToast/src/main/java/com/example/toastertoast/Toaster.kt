package com.example.toastertoast

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

object Toaster {

        fun Toast(context : Context,msg: String){
                android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_SHORT).show()
        }

        @SuppressLint("RestrictedApi")
        fun showCustomToast (view: View, message: String) {
//                val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
//                val snackbarView = snackbar.view
//
//                // Set text color
//                val textView = snackbarView.findViewById(R.id.txtMsg) as? TextView
//                textView?.setTextColor(ContextCompat.getColor(view.context, android.R.color.white))
//
//                // Set position
//                val params = snackbarView.layoutParams as? FrameLayout.LayoutParams
//                params?.gravity = Gravity.BOTTOM
//                snackbarView.layoutParams = params
//
//                snackbar.show()

                val customView = LayoutInflater.from(view.context).inflate(R.layout.custom_toast, null)

                // Find your custom TextView (if you want to modify the text programmatically)
                val txtMsg = customView.findViewById<TextView>(R.id.txtMsg)
                txtMsg.text = message

                // Create a Snackbar
                val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)

                // Get the Snackbar's layout
                val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

                // Remove the default text and action views
                snackbarLayout.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).visibility = View.INVISIBLE
                snackbarLayout.findViewById<TextView>(com.google.android.material.R.id.snackbar_action).visibility = View.INVISIBLE

                // Add your custom view to the Snackbar's layout
                snackbarLayout.addView(customView, 0)

                // Show the Snackbar
                snackbar.show()
        }


}