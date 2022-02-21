package ua.dimaevseenko.format_player.fragment.player.navigation.profile.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.databinding.FragmentPaymentsBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.profile.ProfileFragment
import ua.dimaevseenko.format_player.model.Payments
import ua.dimaevseenko.format_player.viewmodel.ClientViewModel
import javax.inject.Inject

class PaymentsFragment @Inject constructor(): AnimatedFragment(), TabLayout.OnTabSelectedListener {

    companion object{
        const val TAG = "PaymentsFragment"
    }

    private lateinit var binding: FragmentPaymentsBinding
    private lateinit var clientViewModel: ClientViewModel

    @Inject lateinit var paymentsRecyclerViewAdapterFactory: PaymentsRecyclerViewAdapter.Factory
    private lateinit var paymentsRecyclerViewAdapter: PaymentsRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPaymentsBinding.bind(inflater.inflate(R.layout.fragment_payments, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        clientViewModel = ViewModelProvider(requireActivity()).get(ClientViewModel::class.java)

        if(savedInstanceState == null)
            animateStartX()

        binding.backImageView.setOnClickListener { dismiss() }
        initView(clientViewModel.getClient()!!.payments!!)
    }

    private fun initView(payments: Payments){
        paymentsRecyclerViewAdapter = paymentsRecyclerViewAdapterFactory.createAdapter(payments.getPaymentsIn())

        binding.paymentsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.paymentsRecyclerView.adapter = paymentsRecyclerViewAdapter

        binding.paymentsTabLayout.addOnTabSelectedListener(this)
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        if(tab.text == getString(R.string.payment_in))
            paymentsRecyclerViewAdapter.updatePayments(clientViewModel.getClient()!!.payments!!.getPaymentsIn())
        else
            paymentsRecyclerViewAdapter.updatePayments(clientViewModel.getClient()!!.payments!!.getPaymentsOut())
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {}

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