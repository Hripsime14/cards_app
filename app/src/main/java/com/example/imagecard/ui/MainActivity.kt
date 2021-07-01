package com.example.imagecard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.imagecard.R
import com.example.imagecard.ui.feature.CardFragment

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        openCardFragment()
    }

    private fun openCardFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, CardFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    private fun setToolbar() {
        toolbar = findViewById(R.id.toolbar_id)
        setSupportActionBar(toolbar)
        title = ""
    }
}