package ua.dimaevseenko.format_player.fragment.player.navigation.profile.bonus.gift

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.base.BaseFragment
import ua.dimaevseenko.format_player.databinding.FragmentBonusRecyclerBinding
import ua.dimaevseenko.format_player.viewmodel.BonusViewModel
import javax.inject.Inject

class GiftBuyHistoryFragment @Inject constructor(): BaseFragment() {

    companion object{
        const val TAG = "GiftBuyHistoryFragment"
    }

    private lateinit var binding: FragmentBonusRecyclerBinding

    @Inject lateinit var giftBuyHistoryRecyclerViewAdapterFactory: GiftBuyHistoryRecyclerViewAdapter.Factory
    private lateinit var giftBuyHistoryRecyclerViewAdapter: GiftBuyHistoryRecyclerViewAdapter

    private lateinit var bonusViewModel: BonusViewModel

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
        giftBuyHistoryRecyclerViewAdapter = giftBuyHistoryRecyclerViewAdapterFactory.createAdapter(bonusViewModel.getBonus()!!.purchasedGifts!!)

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = giftBuyHistoryRecyclerViewAdapter
    }

    override fun tag(): String {
        return TAG
    }
}