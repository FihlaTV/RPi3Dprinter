package lt.andro.rpi3printerremotecontrol

import android.util.Log

inline fun log(lambda: () -> String) {
    if (BuildConfig.DEBUG) {
        Log.d("TAG", lambda())
    }
}