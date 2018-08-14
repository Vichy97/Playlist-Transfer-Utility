package com.vincent.playlisttransferutility.pages.googlelogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.databinding.FragmentGoogleLoginDialogBinding

class GoogleLoginDialogFragment : DialogFragment() {

    private lateinit var viewModel: GoogleLoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(GoogleLoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_google_login_dialog, container, false)

        val binding: FragmentGoogleLoginDialogBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_google_login_dialog, container, false)
        binding.viewModel = viewModel

        return view
    }

}