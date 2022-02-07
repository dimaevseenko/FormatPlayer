package ua.dimaevseenko.format_player.app

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.Settings
import android.util.Base64
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ua.dimaevseenko.format_player.app.Config.Utils.toHex
import ua.dimaevseenko.format_player.model.LastWatchedChannels
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

object Config {

    const val SERVER_ADDRESS = "https://edge.format-tv.net:10443/"
    const val SERVER_ADDRESS_F = "https://mobileapp.format-tv.net/formatplayer/"

    fun getFullToken(context: Context): String{
        return "${Values.mToken}.${Device.getUniqueDeviceID(context)}"
    }

    object Values{
        private const val PREFERENCES_NAME = "config"

        var login: String? = null
        var mToken: String? = null

        var lastWatchedChannelsIds = LastWatchedChannels()

        fun save(context: Context){
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit().apply {
                putString("login", login)
                putString("mToken", mToken)
                putString("lastWatched", Gson().toJson(lastWatchedChannelsIds))
                apply()
            }
        }

        fun load(context: Context){
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).apply {
                login = getString("login", null)
                mToken = getString("mToken", null)

                getString("lastWatched", null)?.let {
                    val type = object : TypeToken<LastWatchedChannels>() {}.type
                    lastWatchedChannelsIds = Gson().fromJson(it, type)
                }
            }
        }
    }

    object Device{
        val info = "OS: " + Build.VERSION.RELEASE + "; MNFCTRR: " + Build.MANUFACTURER +
                "; MDL: " + Build.MODEL

        fun getUniqueDeviceID(context: Context): String {
            return Utils.md5(
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) + Build.DEVICE + Build.DISPLAY + Build.BOARD + Build.HARDWARE + Build.ID +
                        Build.MANUFACTURER + Build.MODEL + Build.BRAND + Build.HOST + Build.PRODUCT + Build.TAGS + Build.TYPE
            ).toHex()
        }
    }

    object Utils{
        fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))
        fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }

        fun encodeBase64(string: String): String{
            return Base64.encodeToString(string.toByteArray(), Base64.DEFAULT)
        }

        fun encodeBase64ToBitmap(byteArray: ByteArray): Bitmap {
           return BitmapFactory.decodeByteArray(
               byteArray,
               0,
               byteArray.size
           )
        }
    }
}
