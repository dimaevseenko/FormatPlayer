package ua.dimaevseenko.format_player.fragment.player.navigation.profile.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.databinding.FragmentPersonalBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.profile.ProfileFragment
import ua.dimaevseenko.format_player.isTV
import ua.dimaevseenko.format_player.model.Info
import ua.dimaevseenko.format_player.viewmodel.ClientViewModel
import java.util.*
import javax.inject.Inject

class PersonalFragment @Inject constructor(): AnimatedFragment() {

    companion object{
        const val TAG = "PersonalFragment"
    }

    private lateinit var binding: FragmentPersonalBinding

    private lateinit var clientViewModel: ClientViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPersonalBinding.bind(inflater.inflate(R.layout.fragment_personal, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        clientViewModel = ViewModelProvider(requireActivity()).get(ClientViewModel::class.java)

        if(savedInstanceState == null)
            animateStartX()

        if(requireContext().isTV)
            binding.backImageView.requestFocus()

        binding.backImageView.setOnClickListener { dismiss() }

        initView(clientViewModel.getClient()!!.info!!)
    }

    private fun initView(info: Info){
        binding.personalLoginTextView.text = info.login
        binding.personalTariff.text = info.tariff
        binding.personalBalance.text = info.deposit
        binding.personalBonusBalance.text = info.bonus
        binding.personalPeriodEnd.text = info.periodEnd
        binding.personalState.text = if(info.state == "0") getString(R.string.not_active) else getString(R.string.active)
        binding.personalFio.text = "${info.lastName} ${info.firstName} ${info.middleName}"
        binding.personalAddress.text = "${info.address.street} ${info.address.house}, ${info.address.apartment}"
        binding.personalEmail.text = info.email
        binding.personalCurrentState.text = Date(System.currentTimeMillis()).toString()
    }

    fun dismiss(){
        if(isAnimated) {
            animateEndX()
            (parentFragment as ProfileFragment).profileWaysFragment()
        }
    }

    override fun tag(): String {
        return TAG
    }
}