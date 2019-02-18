package com.vincent.playlisttransferutility.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseDialogFragment<V : BaseViewModel> : DialogFragment() {

    @Inject
    protected lateinit var viewModelFactory: BaseViewModelFactory
    protected lateinit var viewModel: V
    protected val compositeDisposable = CompositeDisposable()

    protected abstract fun subscribeToViewModelEvents()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelFactory.getViewModelClass()) as V
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