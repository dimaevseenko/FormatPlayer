package ua.dimaevseenko.format_player.fragment.player.navigation.profile.bonus.auction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.base.BaseFragment
import ua.dimaevseenko.format_player.databinding.FragmentBonusRecyclerBinding
import ua.dimaevseenko.format_player.viewmodel.BonusViewModel
import javax.inject.Inject

class AuctionsFragment @Inject constructor(): BaseFragment() {

    companion object{
        const val TAG = "AuctionFragment"
    }

    private lateinit var binding: FragmentBonusRecyclerBinding

    private lateinit var bonusViewModel: BonusViewModel

    @Inject lateinit var auctionRecyclerViewAdapterFactory: AuctionRecyclerViewAdapter.Factory
    private lateinit var auctionRecyclerViewAdapter: AuctionRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBonusRecyclerBinding.bind(inflater.inflate(R.layout.fragment_bonus_recycler, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)
        bonusViewModel = ViewModelProvider(requireActivity()).get(BonusViewModel::class.java)
        initView()
    }

    private fun initView(){
        auctionRecyclerViewAdapter = auctionRecyclerViewAdapterFactory.createAdapter(bonusViewModel.getBonus()!!.auctions!!)

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = auctionRecyclerViewAdapter
    }

    override fun tag(): String {
        return TAG
    }
}