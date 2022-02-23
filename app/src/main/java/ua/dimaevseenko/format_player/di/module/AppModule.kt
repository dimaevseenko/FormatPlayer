package ua.dimaevseenko.format_player.di.module

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.upstream.DefaultDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ua.dimaevseenko.format_player.network.request.*

@Module(includes = [PlayerModule::class, NetworkModule::class, AnimationModule::class])
object AppModule

@Module
object PlayerModule{

    @Provides
    fun providePlayer(context: Context): ExoPlayer{
        return ExoPlayer.Builder(context).build()
    }

    @Provides
    fun provideDataSourceFactory(context: Context): DefaultDataSource.Factory{
        return DefaultDataSource.Factory(context)
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

    @Provides
    fun provideRPrograms(@MobileServer retrofit: Retrofit): RPrograms{
        return retrofit
            .create(RPrograms::class.java)
    }

    @Provides
    fun provideRClient(@Format24 retrofit: Retrofit): RClient{
        return retrofit.
                create(RClient::class.java)
    }

    @Provides
    fun provideRBonus(@Format24 retrofit: Retrofit): RBonus{
        return retrofit
            .create(RBonus::class.java)
    }
}