package com.vincent.playlisttransferutility.ui.googlelogin

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.app.ActivityCompat
import com.vincent.playlisttransferutility.R
import com.vincent.playlisttransferutility.ui.base.BaseDialogFragment

class GoogleLoginDialogFragment : BaseDialogFragment<GoogleLoginViewModel>() {

    companion object {
        val TAG: String = GoogleLoginDialogFragment::class.java.simpleName
        const val REQUEST_CODE: Int = 125
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_google_login_dialog
    }

    override fun subscribeToViewModelEvents() {
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