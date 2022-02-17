package ua.dimaevseenko.format_player.di.module

import android.annotation.SuppressLint
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.dimaevseenko.format_player.app.Config
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Qualifier
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@Module(includes = [OkHttpModule::class])
object RetrofitModule{

    @EdgeServer
    @Provides
    fun provideRetrofitEdge(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Config.SERVER_ADDRESS)
            .client(okHttpClient)
            .build()
    }

    @MobileServer
    @Provides
    fun provideRetrofitMobile(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Config.SERVER_ADDRESS_F)
            .client(okHttpClient)
            .build()
    }

    @Format24
    @Provides
    fun provideRetrofitFormat24(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Config.SERVER_ADDRESS_FORMAT24)
            .client(okHttpClient)
            .build()
    }
}

@Module
object OkHttpModule{

    @Provides
    fun provideOkHttpClient(): OkHttpClient{
        val trustManager = @SuppressLint("CustomX509TrustManager")
        object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> { return emptyArray() }
        }

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf(trustManager), SecureRandom())

        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustManager)
            .hostnameVerifier { _, _ -> true }
            .build()
    }
}

@Qualifier
annotation class EdgeServer

@Qualifier
annotation class MobileServer

@Qualifier
annotation class Format24