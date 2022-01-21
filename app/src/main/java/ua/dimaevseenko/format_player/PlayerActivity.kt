package ua.dimaevseenko.format_player

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.base.BaseActivity

class PlayerActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(R.layout.activity_player)
        findViewById<Button>(R.id.button).setOnClickListener {
            Config.Values.login = null
            Config.Values.mToken = null
            Config.Values.save(this)
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}