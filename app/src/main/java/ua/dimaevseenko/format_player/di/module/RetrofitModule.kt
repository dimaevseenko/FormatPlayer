package ua.dimaevseenko.format_player.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.dimaevseenko.format_player.app.Config
import javax.inject.Qualifier

@Module
object RetrofitModule{

    @EdgeServer
    @Provides
    fun provideRetrofitEdge(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Config.SERVER_ADDRESS)
            .build()
    }

    @MobileServer
    @Provides
    fun provideRetrofitMobile(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Config.SERVER_ADDRESS_F)
            .build()
    }
}

@Qualifier
annotation class EdgeServer

@Qualifier
annotation class MobileServer