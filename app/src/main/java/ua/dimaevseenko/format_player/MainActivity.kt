package ua.dimaevseenko.format_player

import android.os.Bundle
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.base.BaseActivity
import ua.dimaevseenko.format_player.fragment.MainFragment
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var mainFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(R.layout.activity_main)

        Config.Values.load(this)

        if(savedInstanceState == null)
            addFragment(R.id.mainContainer, mainFragment, MainFragment.TAG)
        else
            mainFragment = getFragment(MainFragment.TAG)!!
    }

    override fun onBackPressed() {
        if(!mainFragment.onBackPressed())
            super.onBackPressed()
    }
}