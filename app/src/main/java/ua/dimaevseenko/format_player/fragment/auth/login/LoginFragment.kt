package ua.dimaevseenko.format_player.fragment.auth.login

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.databinding.FragmentLoginBinding
import ua.dimaevseenko.format_player.fragment.auth.AuthorizationFragment
import ua.dimaevseenko.format_player.fragment.RequestViewModel
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.LoginResult
import javax.inject.Inject

class LoginFragment @Inject constructor(): Fragment(), Server.Listener<LoginResult> {

    companion object{
        const val TAG = "LoginFragment"
    }

    private lateinit var binding: FragmentLoginBinding

    private var isShowingPassword = false

    @Inject lateinit var loginViewModelFactory: RequestViewModel.Factory<LoginResult>
    private lateinit var viewModel: RequestViewModel<LoginResult>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.bind(inflater.inflate(R.layout.fragment_login, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        viewModel = ViewModelProvider(viewModelStore, loginViewModelFactory).get(RequestViewModel::class.java) as RequestViewModel<LoginResult>
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
        showProgressDialog()
        viewModel.request(
            Bundle().apply {
                putString("action", "jadddevice")
                putString("login", binding.loginEditText.editableText.toString())
                putString("password", binding.passwordEditText.editableText.toString())
            }
        )
    }

    override fun onDestroy() {
        viewModel.listener = null
        super.onDestroy()
    }

    override fun onResponse(result: LoginResult) {
        dismissProgressDialog()
        if(result.status == 0)
            loginSuccess(result.mToken)
        else
            loginFailed(result.msg)
    }

    private fun loginSuccess(token: String){
        Config.Values.login = binding.loginEditText.editableText.toString()
        Config.Values.mToken = token
        Config.Values.save(requireContext())
        (parentFragment as AuthorizationFragment).loginSuccess()
    }

    private fun loginFailed(reason: String){
       if(getString(R.string.no_iptv).contains(reason)){
           println("sss")
       }else if(getString(R.string.invalid_password).contains(reason))
           println("invalid")
    }

    override fun onFailure(t: Throwable) {
        dismissProgressDialog()
    }
}