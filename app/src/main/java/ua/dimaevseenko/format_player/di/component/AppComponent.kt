package ua.dimaevseenko.format_player.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ua.dimaevseenko.format_player.MainActivity
import ua.dimaevseenko.format_player.PlayerActivity
import ua.dimaevseenko.format_player.base.BaseActivity
import ua.dimaevseenko.format_player.di.module.AppModule
import ua.dimaevseenko.format_player.fragment.main.MainFragment
import ua.dimaevseenko.format_player.fragment.main.auth.AuthorizationFragment
import ua.dimaevseenko.format_player.fragment.main.auth.login.LoginFragment
import ua.dimaevseenko.format_player.fragment.main.auth.register.RegisterFragment
import ua.dimaevseenko.format_player.fragment.main.splash.SplashFragment

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(splashFragment: SplashFragment)
    fun inject(authorizationFragment: AuthorizationFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(registerFragment: RegisterFragment)

    fun inject(playerActivity: PlayerActivity)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun bindContext(context: Context): Builder
        fun build(): AppComponent
    }
}