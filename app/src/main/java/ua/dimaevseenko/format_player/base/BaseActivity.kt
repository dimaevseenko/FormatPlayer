package ua.dimaevseenko.format_player.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ua.dimaevseenko.format_player.dialog.ProgressDialogFragment
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.getFragment
import javax.inject.Inject

open class BaseActivity: AppCompatActivity() {

    @Inject
    lateinit var progressDialog: ProgressDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    internal fun showProgressDialog(){
        progressDialog.show(supportFragmentManager, ProgressDialogFragment.TAG)
        progressDialog.isCancelable = false
    }

    internal fun dismissProgressDialog(){
        getFragment<ProgressDialogFragment>(ProgressDialogFragment.TAG)?.let { progressDialog = it }
        progressDialog.dismiss()
    }
}