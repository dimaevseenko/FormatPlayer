package ua.dimaevseenko.format_player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ua.dimaevseenko.format_player.fragment.main.MainFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(R.layout.activity_main)

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