package ua.dimaevseenko.format_player.fragment.main.auth.login

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.Lazy
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.base.BaseActivity
import ua.dimaevseenko.format_player.databinding.FragmentLoginBinding
import ua.dimaevseenko.format_player.fragment.main.auth.AuthorizationFragment
import ua.dimaevseenko.format_player.isTV
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.LoginResult
import javax.inject.Inject

class LoginFragment @Inject constructor(): Fragment(), Server.Listener<LoginResult> {

    companion object{
        const val TAG = "LoginFragment"
    }

    private lateinit var binding: FragmentLoginBinding

    private var isShowingPassword = false

    @Inject lateinit var loginViewModelFactory: LoginViewModel.Factory
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.bind(inflater.inflate(R.layout.fragment_login, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireContext().appComponent.inject(this)

        viewModel = ViewModelProvider(viewModelStore, loginViewModelFactory).get(LoginViewModel::class.java)
        viewModel.listener = this

        if(requireContext().isTV)
            binding.loginEditText.requestFocus()

        binding.showPasswordButton.setOnClickListener { showPassword() }
        binding.registerButton.setOnClickListener { (parentFragment as AuthorizationFragment).registerFragment() }
        binding.loginButton.setOnClickListener { login() }
    }

    private fun showPassword(){
        if(isShowingPassword) {
            binding.passwordEditText.transformationMethod = PasswordTransformationMethod()
            binding.showPasswordButton.setImageResource(R.drawable.ic_hide_password)
        } else {
            binding.passwordEditText.transformationMethod = null
            binding.showPasswordButton.setImageResource(R.drawable.ic_show_password)
        }
        
        isShowingPassword = !isShowingPassword
    }

    private fun login(){
        (requireActivity() as BaseActivity).showProgressDialog()

        viewModel.login(
            binding.loginEditText.editableText.toString(),
            binding.passwordEditText.editableText.toString()
        )
    }

    override fun onDestroy() {
        viewModel.listener = null
        super.onDestroy()
    }

    override fun onResponse(result: LoginResult) {
        (requireActivity() as BaseActivity).dismissProgressDialog()
        if(result.status == 0){
            Config.Values.login = binding.loginEditText.editableText.toString()
            Config.Values.mToken = result.mToken
            Config.Values.save(requireContext())
        }
    }

    override fun onFailure(t: Throwable) {
        (requireActivity() as BaseActivity).dismissProgressDialog()
    }
}