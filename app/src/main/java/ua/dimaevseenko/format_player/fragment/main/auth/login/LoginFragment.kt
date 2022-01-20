package ua.dimaevseenko.format_player.fragment.main.auth.login

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.base.BaseActivity
import ua.dimaevseenko.format_player.databinding.FragmentLoginBinding
import ua.dimaevseenko.format_player.fragment.main.auth.AuthorizationFragment
import ua.dimaevseenko.format_player.isTV
import javax.inject.Inject

class LoginFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "LoginFragment"
    }

    private lateinit var binding: FragmentLoginBinding

    private var isShowingPassword = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.bind(inflater.inflate(R.layout.fragment_login, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireContext().appComponent.inject(this)
        if(requireContext().isTV)
            binding.loginEditText.requestFocus()

        binding.showPasswordButton.setOnClickListener { showPassword() }
        binding.registerButton.setOnClickListener { (parentFragment as AuthorizationFragment).registerFragment() }
        binding.loginButton.setOnClickListener { (requireActivity() as BaseActivity).showProgressDialog() }
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
}