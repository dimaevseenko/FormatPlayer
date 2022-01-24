package ua.dimaevseenko.format_player.fragment.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.databinding.FragmentRegisterBinding
import ua.dimaevseenko.format_player.fragment.auth.AuthorizationFragment
import ua.dimaevseenko.format_player.fragment.RequestViewModel
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.RegisterResult
import javax.inject.Inject

class RegisterFragment @Inject constructor(): Fragment(), Server.Listener<RegisterResult> {

    companion object{
        const val TAG = "RegisterFragment"
    }

    private lateinit var binding: FragmentRegisterBinding

    @Inject lateinit var registerViewModelFactory: RequestViewModel.Factory<RegisterResult>
    private lateinit var viewModel: RequestViewModel<RegisterResult>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.bind(inflater.inflate(R.layout.fragment_register, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        viewModel = ViewModelProvider(viewModelStore, registerViewModelFactory).get(RequestViewModel::class.java) as RequestViewModel<RegisterResult>
        viewModel.listener = this

        if(requireContext().isTV)
            binding.buttonBack.requestFocus()

        binding.buttonBack.setOnClickListener { (parentFragment as AuthorizationFragment).loginFragment() }
        binding.buttonSend.setOnClickListener { register() }
    }

    private fun register(){
        showProgressDialog()
        viewModel.request(
            Bundle().apply {
                putString("action", "jadduser")
                putString("phone", binding.editTextPhoneNumber.editableText.toString())
                putString("name", binding.editTextFio.editableText.toString())
            }
        )
    }

    override fun onResponse(result: RegisterResult) {
        dismissProgressDialog()
        if(result.status == 0)
            registerSuccess()
        else
            registerFailed(result.msg)
    }

    private fun registerSuccess(){
        println("success")
    }

    private fun registerFailed(msg: String){
        if(getString(R.string.invalid_number).contains(msg)){
            println("bad number")
        }else if(getString(R.string.number_already_exits).contains(msg)){
            println("already exits")
        }
    }

    override fun onFailure(t: Throwable) {
        dismissProgressDialog()
    }

    override fun onDestroy() {
        viewModel.listener = null
        super.onDestroy()
    }
}