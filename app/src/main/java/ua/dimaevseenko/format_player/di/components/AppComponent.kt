package ua.dimaevseenko.format_player.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ua.dimaevseenko.format_player.MainActivity

@Component
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun bindContext(context: Context): Builder
        fun build(): AppComponent
    }
}