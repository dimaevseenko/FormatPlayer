package ua.dimaevseenko.format_player.di.module

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.network.RUser

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
    fun provideRUser(retrofit: Retrofit): RUser{
        return retrofit
            .create(RUser::class.java)
    }
}

@Module
object RetrofitModule{

    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Config.SERVER_ADDRESS)
            .build()
    }
}