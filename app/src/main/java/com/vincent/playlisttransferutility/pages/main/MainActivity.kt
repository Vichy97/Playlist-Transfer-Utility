package com.vincent.playlisttransferutility.pages.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.vincent.playlisttransferutility.R

class MainActivity : AppCompatActivity() {

    var mainViewModel: MainViewModel = MainViewModel(MainModel());

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }
}
