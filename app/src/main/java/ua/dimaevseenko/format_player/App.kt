package ua.dimaevseenko.format_player

import android.app.Application
import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import ua.dimaevseenko.format_player.di.components.AppComponent
import ua.dimaevseenko.format_player.di.components.DaggerAppComponent

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

fun FragmentActivity.addFragment(container: Int, fragment: Fragment, tag: String){
    supportFragmentManager.beginTransaction().add(container, fragment, tag).commit()
}

fun FragmentActivity.replaceFragment(container: Int, fragment: Fragment, tag: String){
    supportFragmentManager.beginTransaction().replace(container, fragment, tag).commit()
}

fun <T: Fragment> FragmentActivity.getFragment(tag: String): T?{
    return supportFragmentManager.findFragmentByTag(tag) as? T
}

fun FragmentActivity.removeFragment(fragment: Fragment){
    supportFragmentManager.beginTransaction().remove(fragment).commit()
}

fun Fragment.addFragment(container: Int, fragment: Fragment, tag: String, animated: Boolean = false){
    childFragmentManager.beginTransaction().apply {
        if(animated)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        add(container, fragment, tag)
        commit()
    }
}

fun Fragment.replaceFragment(container: Int, fragment: Fragment, tag: String, animated: Boolean = false){
    childFragmentManager.beginTransaction().apply {
        if(animated)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        replace(container, fragment, tag)
        commit()
    }
}

fun <T: Fragment> Fragment.getFragment(tag: String): T?{
    return childFragmentManager.findFragmentByTag(tag) as? T
}

fun Fragment.removeFragment(fragment: Fragment){
    childFragmentManager.beginTransaction().remove(fragment).commit()
}

val Context.isTV: Boolean
    get() = (getSystemService(Context.UI_MODE_SERVICE) as UiModeManager)
        .currentModeType == Configuration.UI_MODE_TYPE_TELEVISION
