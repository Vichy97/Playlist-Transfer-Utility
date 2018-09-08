package com.vincent.playlisttransferutility.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.vincent.playlisttransferutility.R
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    protected lateinit var navController: NavController

    protected val compositeDisposable: CompositeDisposable

    init {
        compositeDisposable = CompositeDisposable()
    }

    protected abstract fun subscribeToViewModelEvents()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = Navigation.findNavController(activity!!, R.id.nav_host)
    }

    override fun onResume() {
        super.onResume()

        subscribeToViewModelEvents()
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.clear()
    }
}