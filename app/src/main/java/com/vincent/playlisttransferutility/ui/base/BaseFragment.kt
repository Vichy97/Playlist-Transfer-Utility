package com.vincent.playlisttransferutility.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.vincent.playlisttransferutility.R
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory
    protected lateinit var navController: NavController

    protected val compositeDisposable = CompositeDisposable()

    protected abstract fun subscribeToViewModelEvents()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        navController = Navigation.findNavController(activity!!, R.id.nav_host)
        return view
    }

    override fun onResume() {
        super.onResume()

        subscribeToViewModelEvents()
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.clear()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
}