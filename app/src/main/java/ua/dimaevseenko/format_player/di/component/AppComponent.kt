package ua.dimaevseenko.format_player.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ua.dimaevseenko.format_player.MainActivity
import ua.dimaevseenko.format_player.base.BaseActivity
import ua.dimaevseenko.format_player.di.module.AppModule
import ua.dimaevseenko.format_player.fragment.MainFragment
import ua.dimaevseenko.format_player.fragment.auth.AuthorizationFragment
import ua.dimaevseenko.format_player.fragment.auth.login.LoginFragment
import ua.dimaevseenko.format_player.fragment.auth.register.RegisterFragment
import ua.dimaevseenko.format_player.fragment.splash.SplashFragment
import ua.dimaevseenko.format_player.fragment.player.PlayerFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.PlayerNavFragment

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(splashFragment: SplashFragment)
    fun inject(authorizationFragment: AuthorizationFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(registerFragment: RegisterFragment)
    fun inject(playerFragment: PlayerFragment)
    fun inject(playerNavFragment: PlayerNavFragment)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun bindContext(context: Context): Builder
        fun build(): AppComponent
    }
}