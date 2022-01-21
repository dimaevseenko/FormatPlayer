package ua.dimaevseenko.format_player.fragment.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentRegisterBinding
import ua.dimaevseenko.format_player.fragment.auth.AuthorizationFragment
import ua.dimaevseenko.format_player.isTV
import javax.inject.Inject

class RegisterFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "RegisterFragment"
    }

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.bind(inflater.inflate(R.layout.fragment_register, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        if(requireContext().isTV)
            binding.buttonBack.requestFocus()

        binding.buttonBack.setOnClickListener { (parentFragment as AuthorizationFragment).loginFragment() }
    }
}