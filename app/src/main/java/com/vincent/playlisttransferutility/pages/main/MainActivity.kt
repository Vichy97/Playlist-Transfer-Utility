package com.vincent.playlisttransferutility.pages.main

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.databinding.ActivityMainBinding
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private var mainViewModel: MainViewModel = MainViewModel(MainModel())

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupDataBinding()
    }

    private fun setupDataBinding() {
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mainViewModel
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.clear()
    }

    override fun onResume() {
        super.onResume()

        compositeDisposable.add(mainViewModel.getToastMessage().subscribe {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}
