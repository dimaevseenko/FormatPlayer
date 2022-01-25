package ua.dimaevseenko.format_player.di.module

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ua.dimaevseenko.format_player.network.request.RPlaylist
import ua.dimaevseenko.format_player.network.request.RUser

@Module(includes = [PlayerModule::class, NetworkModule::class])
object AppModule

@Module
object PlayerModule{

    @Provides
    fun providePlayer(context: Context): ExoPlayer{
        return ExoPlayer.Builder(context).build()
    }
}

@Module(includes = [RetrofitModule::class])
object NetworkModule{

    @Provides
    fun provideRUser(@EdgeServer retrofit: Retrofit): RUser {
        return retrofit
            .create(RUser::class.java)
    }

    @Provides
    fun provideRPlaylist(@EdgeServer retrofit: Retrofit): RPlaylist{
        return retrofit
            .create(RPlaylist::class.java)
    }
}