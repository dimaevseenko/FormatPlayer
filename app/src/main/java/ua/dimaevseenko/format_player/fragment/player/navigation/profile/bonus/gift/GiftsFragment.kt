package ua.dimaevseenko.format_player.fragment.player.navigation.profile.bonus.gift

import android.os.Bundle
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

class GiftsFragment @Inject constructor(): BaseFragment() {

    companion object{
        const val TAG = "GiftFragment"
    }

    private lateinit var binding: FragmentBonusRecyclerBinding

    private lateinit var bonusViewModel: BonusViewModel

    @Inject lateinit var giftsRecyclerViewAdapterFactory: GiftsRecyclerViewAdapter.Factory
    private lateinit var giftsRecyclerViewAdapter: GiftsRecyclerViewAdapter

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
        giftsRecyclerViewAdapter = giftsRecyclerViewAdapterFactory.createAdapter(bonusViewModel.getBonus()!!.gifts!!)

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = giftsRecyclerViewAdapter
    }

    override fun tag(): String {
        return TAG
    }
}