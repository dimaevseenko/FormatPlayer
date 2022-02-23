package ua.dimaevseenko.format_player.network

import android.content.Context
import android.os.Bundle
import dagger.Lazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Callback
import ua.dimaevseenko.format_player.app.Config
import ua.dimaevseenko.format_player.model.Auctions
import ua.dimaevseenko.format_player.model.Bonus
import ua.dimaevseenko.format_player.model.Gifts
import ua.dimaevseenko.format_player.network.request.*
import ua.dimaevseenko.format_player.network.result.*
import javax.inject.Inject

object Server {

    interface Listener<T>{
        fun onResponse(result: T)
        fun onFailure(t: Throwable)
    }

    class Request @Inject constructor(){

        @Inject lateinit var login: Lazy<Login>
        @Inject lateinit var register: Lazy<Register>
        @Inject lateinit var unLogin: Lazy<UnLogin>
        @Inject lateinit var playlist: Lazy<Playlist>
        @Inject lateinit var icons: Lazy<Icons>
        @Inject lateinit var programs: Lazy<Programs>
        @Inject lateinit var client: Lazy<Client>
        @Inject lateinit var bonus: Lazy<Bonus>

        fun<T> request(bundle: Bundle, callback: Callback<T>){
            when(bundle.getString("action")){
                "jadddevice" -> { login.get().login(bundle, callback = callback as Callback<LoginResult>) }
                "jadduser" -> { register.get().register(bundle, callback = callback as Callback<RegisterResult>) }
                "jdeldevice" -> { unLogin.get().unLogin(callback = callback as Callback<UnLoginResult>) }
                "jgetchannellist" -> { playlist.get().getPlaylist(callback = callback as Callback<PlaylistResult>) }
                "jgetjsoniconschannels" -> { icons.get().getIcons(callback = callback as Callback<IconsResult>) }
                "getProgramsById" -> { programs.get().getPrograms(bundle, callback = callback as Callback<ProgramsResult>) }
                "authClient" -> { client.get().authClient(callback = callback as Callback<ClientResult>) }
                "getClientInfo" -> { client.get().getClientInfo(bundle, callback = callback as Callback<InfoResult>)}
                "getClientPayments" -> { client.get().getClientPayments(bundle, callback = callback as Callback<PaymentsResult>) }
                "getBonusGiftItems" -> { bonus.get().getBonusGiftItems(callback = callback as Callback<Gifts>) }
                "getBonusGiftBuyHistory" -> { bonus.get().getBonusGiftButHistory(callback = callback as Callback<Gifts>) }
                "getBonusAuctionItems" -> { bonus.get().getBonusAuctionItems(callback = callback as Callback<Auctions>) }
                "getBonusAuctionBetHistory" -> { bonus.get().getBonusAuctionBetHistory(callback = callback as Callback<Auctions>) }
            }
        }
    }

    class Login @Inject constructor(){
        private lateinit var authmac: String
        @Inject lateinit var rUser: RUser

        fun login(bundle: Bundle, callback: Callback<LoginResult>){
            CoroutineScope(Dispatchers.Default).launch {
                rUser.login(authmac = authmac, login = bundle.getString("login")!!, password = Config.Utils.encodeBase64(bundle.getString("password")!!))
                    .enqueue(callback)
            }
        }

        @Inject
        fun inject(context: Context){
            authmac = Config.Device.getUniqueDeviceID(context)
        }
    }

    class Register @Inject constructor(){
        @Inject lateinit var rUser: RUser

        fun register(bundle: Bundle,  callback: Callback<RegisterResult>){
            CoroutineScope(Dispatchers.Default).launch {
                rUser.register(phone = bundle.getString("phone")!!, name = bundle.getString("name")!!).enqueue(callback)
            }
        }
    }

    class UnLogin @Inject constructor(){
        private lateinit var authmac: String
        @Inject lateinit var rUser: RUser

        fun unLogin(callback: Callback<UnLoginResult>){
            CoroutineScope(Dispatchers.Default).launch {
                rUser.unLogin(authmac = authmac).enqueue(callback)
            }
        }

        @Inject
        fun inject(context: Context){
            authmac = Config.getFullToken(context)
        }
    }

    class Playlist @Inject constructor(){
        private lateinit var authmac: String

        @Inject lateinit var rPlaylist: RPlaylist

        fun getPlaylist(callback: Callback<PlaylistResult>){
            CoroutineScope(Dispatchers.Default).launch {
                rPlaylist.getPlaylist(authmac = authmac).enqueue(callback)
            }
        }

        @Inject
        fun inject(context: Context){
            authmac = Config.getFullToken(context)
        }
    }

    class Icons @Inject constructor(){
        @Inject lateinit var rPlaylist: RPlaylist

        fun getIcons(callback: Callback<IconsResult>){
            rPlaylist.getIcons().enqueue(callback)
        }
    }

    class Programs @Inject constructor(){
        @Inject lateinit var rPrograms: RPrograms

        fun getPrograms(bundle: Bundle, callback: Callback<ProgramsResult>){
            rPrograms.getPrograms(id = bundle.getString("id")!!).enqueue(callback)
        }
    }

    class Client @Inject constructor(){
        @Inject lateinit var rClient: RClient

        fun authClient(callback: Callback<ClientResult>){
            rClient.authClient().enqueue(callback)
        }

        fun getClientInfo(bundle: Bundle, callback: Callback<InfoResult>){
            rClient.getClientInfo(clientId = bundle.getString("clientId")!!).enqueue(callback)
        }

        fun getClientPayments(bundle: Bundle, callback: Callback<PaymentsResult>){
            rClient.getClientPayments(clientId = bundle.getString("clientId")!!).enqueue(callback)
        }
    }

    class Bonus @Inject constructor(){
        @Inject lateinit var rBonus: RBonus

        fun getBonusGiftItems(callback: Callback<Gifts>){
            rBonus.getBonusGiftItems().enqueue(callback)
        }

        fun getBonusGiftButHistory(callback: Callback<Gifts>){
            rBonus.getBonusGiftBuyHistory().enqueue(callback)
        }

        fun getBonusAuctionItems(callback: Callback<Auctions>){
            rBonus.getBonusAuctionItems().enqueue(callback)
        }

        fun getBonusAuctionBetHistory(callback: Callback<Auctions>){
            rBonus.getBonusAuctionBetHistory().enqueue(callback)
        }
    }
}