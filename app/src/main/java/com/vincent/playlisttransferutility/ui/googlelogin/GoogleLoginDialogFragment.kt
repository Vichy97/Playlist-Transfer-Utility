package com.vincent.playlisttransferutility.ui.googlelogin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.databinding.FragmentGoogleLoginDialogBinding
import com.vincent.playlisttransferutility.ui.base.ViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import dagger.android.support.AndroidSupportInjection

class GoogleLoginDialogFragment : DialogFragment() {

    companion object {
        val TAG: String = GoogleLoginDialogFragment::class.java.simpleName
        const val REQUEST_CODE: Int = 125
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: GoogleLoginViewModel

    private val compositeDisposable: CompositeDisposable

    init {
        compositeDisposable = CompositeDisposable()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[GoogleLoginViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding: FragmentGoogleLoginDialogBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_google_login_dialog, container, false)
        binding.viewModel = viewModel

        subscribeToViewModelEvents()

        return binding.root
    }

    private fun subscribeToViewModelEvents() {
        compositeDisposable.addAll(viewModel.getPermissionsEvents().subscribe(this::onPermissionsEventReceived),
                viewModel.getToastEvents().subscribe(this::onToastEventReceived))
    }

    private fun onPermissionsEventReceived(permissions: Array<String>) {
        ActivityCompat.requestPermissions(activity!!, permissions, REQUEST_CODE)
    }

    private fun onToastEventReceived(message: String) {
        Toast.makeText(context!!, message, LENGTH_SHORT).show()
    }

}