package ua.dimaevseenko.format_player.fragment.player.navigation.profile.bonus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.FragmentBonusWaysBinding
import ua.dimaevseenko.format_player.viewmodel.BonusViewModel
import javax.inject.Inject

class BonusWaysFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "BonusWaysFragment"
    }

    private lateinit var binding: FragmentBonusWaysBinding

    private lateinit var bonusViewModel: BonusViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBonusWaysBinding.bind(inflater.inflate(R.layout.fragment_bonus_ways, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bonusViewModel = ViewModelProvider(requireActivity()).get(BonusViewModel::class.java)
        bonusViewModel.getBonus()?.let {
            Log.d("CHANNELL", it.gifts!!.size.toString())
            Log.d("CHANNELL", it.purchasedGifts!!.size.toString())
            Log.d("CHANNELL", it.auctions!!.size.toString())
            Log.d("CHANNELL", it.bitAuctions!!.size.toString())
        }
    }
}