package ua.dimaevseenko.format_player.fragment.player.navigation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.dimaevseenko.format_player.*
import ua.dimaevseenko.format_player.databinding.FragmentProfileBinding
import ua.dimaevseenko.format_player.base.AnimatedFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.profile.payments.PaymentsFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.profile.personal.PersonalFragment
import javax.inject.Inject

class ProfileFragment @Inject constructor(): AnimatedFragment(){

    companion object{
        const val TAG = "ProfileFragment"
    }

    private lateinit var binding: FragmentProfileBinding

    @Inject lateinit var profileLoaderFragment: ProfileLoaderFragment
    @Inject lateinit var profileWaysFragment: ProfileWaysFragment
    @Inject lateinit var personalFragment: PersonalFragment
    @Inject lateinit var paymentsFragment: PaymentsFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.bind(inflater.inflate(R.layout.fragment_profile, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(savedInstanceState == null)
            replaceFragment(R.id.profileContainer, profileLoaderFragment, ProfileLoaderFragment.TAG, true)
    }

    fun clientLoaded(){
        if(isAdded)
            replaceFragment(R.id.profileContainer, profileWaysFragment, ProfileWaysFragment.TAG, true)
    }

    fun profileWaysFragment(){
        profileWaysFragment.returnFragment = true
        replaceFragment(R.id.profileContainer, profileWaysFragment, ProfileWaysFragment.TAG, true)
    }

    fun personalFragment(){
        replaceFragment(R.id.profileContainer, personalFragment, PersonalFragment.TAG, true)
    }

    fun paymentsFragment(){
        replaceFragment(R.id.profileContainer, paymentsFragment, PaymentsFragment.TAG, true)
    }

    fun onBackPressed(): Boolean{
        getFragment<PaymentsFragment>(PaymentsFragment.TAG)?.let {
            it.dismiss()
            return true
        }
        getFragment<PersonalFragment>(PersonalFragment.TAG)?.let {
            it.dismiss()
            return true
        }
        return false
    }

    override fun tag(): String {
        return TAG
    }
}