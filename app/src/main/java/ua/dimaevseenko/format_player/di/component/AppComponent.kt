package ua.dimaevseenko.format_player.di.component

import android.content.Context
import android.view.animation.ScaleAnimation
import dagger.BindsInstance
import dagger.Component
import ua.dimaevseenko.format_player.MainActivity
import ua.dimaevseenko.format_player.base.BaseActivity
import ua.dimaevseenko.format_player.di.module.AppModule
import ua.dimaevseenko.format_player.di.module.FocusAnimation
import ua.dimaevseenko.format_player.di.module.UnFocusAnimation
import ua.dimaevseenko.format_player.fragment.MainFragment
import ua.dimaevseenko.format_player.fragment.auth.AuthorizationFragment
import ua.dimaevseenko.format_player.fragment.auth.login.LoginFragment
import ua.dimaevseenko.format_player.fragment.auth.register.RegisterFragment
import ua.dimaevseenko.format_player.fragment.splash.SplashFragment
import ua.dimaevseenko.format_player.fragment.player.PlayerFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.LoaderFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.PlayerNavFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.home.channels.ChannelsFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.home.HomeFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.home.cameras.CamerasFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.profile.ProfileFragment
import ua.dimaevseenko.format_player.fragment.player.stream.StreamFragment
import ua.dimaevseenko.format_player.fragment.player.stream.camera.CameraStreamFragment
import ua.dimaevseenko.format_player.fragment.player.stream.channel.ChannelControlsFragment
import ua.dimaevseenko.format_player.fragment.player.stream.channel.ChannelProgramsFragment
import ua.dimaevseenko.format_player.fragment.player.stream.channel.settings.ChannelSettingsFragment
import ua.dimaevseenko.format_player.fragment.player.stream.channel.ChannelStreamFragment
import ua.dimaevseenko.format_player.fragment.player.stream.channel.catchup.CatchupFragment
import ua.dimaevseenko.format_player.fragment.player.stream.channel.settings.ChannelQualityFragment

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
    fun inject(homeFragment: HomeFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(channelsFragment: ChannelsFragment)
    fun inject(loaderFragment: LoaderFragment)
    fun inject(camerasFragment: CamerasFragment)
    fun inject(streamFragment: StreamFragment)
    fun inject(channelStreamFragment: ChannelStreamFragment)
    fun inject(cameraStreamFragment: CameraStreamFragment)
    fun inject(channelProgramsFragment: ChannelProgramsFragment)
    fun inject(channelControlsFragment: ChannelControlsFragment)
    fun inject(channelSettingsFragment: ChannelSettingsFragment)
    fun inject(channelQualityFragment: ChannelQualityFragment)
    fun inject(catchupFragment: CatchupFragment)

    fun createChannelStreamFragment(): ChannelStreamFragment
    fun createCameraStreamFragment(): CameraStreamFragment
    fun createCatchupFragment(): CatchupFragment

    fun createChannelSettingsFragment(): ChannelSettingsFragment

    @FocusAnimation
    fun createFocusScaleAnimation(): ScaleAnimation
    @UnFocusAnimation
    fun createUnFocusScaleAnimation(): ScaleAnimation

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun bindContext(context: Context): Builder
        fun build(): AppComponent
    }
}