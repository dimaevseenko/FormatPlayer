package ua.dimaevseenko.format_player.di.components

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Component
import ua.dimaevseenko.format_player.MainActivity
import ua.dimaevseenko.format_player.di.modules.AppModule
import ua.dimaevseenko.format_player.fragments.main.login.LoginFragment
import ua.dimaevseenko.format_player.fragments.main.splash.SplashFragment

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(splashFragment: SplashFragment)
    fun inject(loginFragment: LoginFragment)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun bindContext(context: Context): Builder
        fun build(): AppComponent
    }
}