package com.vincent.playlisttransferutility.ui.base

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    protected lateinit var navController: NavController

    protected val compositeDisposable: CompositeDisposable

    init {
        compositeDisposable = CompositeDisposable()
    }

    protected abstract fun subscribeToViewModelEvents()

    override fun onResume() {
        super.onResume()

        subscribeToViewModelEvents()
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.clear()
    }
}