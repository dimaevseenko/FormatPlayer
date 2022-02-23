package ua.dimaevseenko.format_player.fragment.player.navigation.profile.bonus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentBonusLoaderBinding
import ua.dimaevseenko.format_player.model.Bonus
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.viewmodel.BonusViewModel
import javax.inject.Inject

class BonusLoaderFragment @Inject constructor(): Fragment(), Server.Listener<Bonus> {

    companion object{
        const val TAG = "BonusLoaderFragment"
    }

    private lateinit var binding: FragmentBonusLoaderBinding

    @Inject lateinit var bonusViewModelFactory: BonusViewModel.Factory
    private lateinit var bonusViewModel: BonusViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBonusLoaderBinding.bind(inflater.inflate(R.layout.fragment_bonus_loader, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        bonusViewModel = ViewModelProvider(requireActivity(), bonusViewModelFactory).get(BonusViewModel::class.java)
        bonusViewModel.listener = this
        bonusViewModel.loadBonus()
    }

    override fun onResponse(result: Bonus) {
        (parentFragment as BonusFragment).bonusLoaded()
    }

    override fun onFailure(t: Throwable) {

    }

    override fun onDestroy() {
        bonusViewModel.listener = null
        super.onDestroy()
    }
}