package ua.dimaevseenko.format_player

import android.app.Application
import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import ua.dimaevseenko.format_player.base.BaseActivity
import ua.dimaevseenko.format_player.di.component.AppComponent
import ua.dimaevseenko.format_player.di.component.DaggerAppComponent
import ua.dimaevseenko.format_player.fragment.MainFragment
import ua.dimaevseenko.format_player.fragment.player.PlayerFragment


class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .bindContext(this)
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = if(this is App) appComponent else applicationContext.appComponent

val Fragment.appComponent: AppComponent
    get() = requireContext().appComponent

val Fragment.mainActivity: MainActivity
    get() = requireActivity() as MainActivity

val Fragment.mainFragment: MainFragment
    get() = mainActivity.mainFragment

val Fragment.playerFragment: PlayerFragment
    get() = mainFragment.playerFragment

val Context.isTV: Boolean
    get() = (getSystemService(Context.UI_MODE_SERVICE) as UiModeManager)
        .currentModeType == Configuration.UI_MODE_TYPE_TELEVISION

fun FragmentActivity.addFragment(container: Int, fragment: Fragment, tag: String, animated: Boolean = false){
    supportFragmentManager.beginTransaction().apply {
        if(animated)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        add(container, fragment, tag)
        commit()
    }
}

fun <T: Fragment> FragmentActivity.getFragment(tag: String): T?{
    return supportFragmentManager.findFragmentByTag(tag) as? T
}

fun Fragment.addFragment(container: Int, fragment: Fragment, tag: String, animated: Boolean = false, transaction: Int = FragmentTransaction.TRANSIT_FRAGMENT_OPEN){
    childFragmentManager.beginTransaction().apply {
        if(animated)
            setTransition(transaction)
        add(container, fragment, tag)
        commit()
    }
}

fun Fragment.replaceFragment(container: Int, fragment: Fragment, tag: String, animated: Boolean = false, transaction: Int = FragmentTransaction.TRANSIT_FRAGMENT_FADE){
    childFragmentManager.beginTransaction().apply {
        if(animated)
            if(transaction == FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            else
                setTransition(transaction)
        replace(container, fragment, tag)
        commit()
    }
}

fun <T: Fragment> Fragment.getFragment(tag: String): T?{
    return childFragmentManager.findFragmentByTag(tag) as? T
}

fun Fragment.removeFragment(fragment: Fragment, animated: Boolean = false, transaction: Int = FragmentTransaction.TRANSIT_FRAGMENT_OPEN){
    childFragmentManager.beginTransaction().apply {
        if(animated)
            setTransition(transaction)
        remove(fragment)
        commit()
    }
}

fun Fragment.showProgressDialog(){
    (requireActivity() as BaseActivity).showProgressDialog()
}

fun Fragment.dismissProgressDialog(){
    (requireActivity() as BaseActivity).dismissProgressDialog()
}