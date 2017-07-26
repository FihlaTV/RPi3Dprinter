package lt.andro.rpi3dprinter
import android.util.Log
import lt.andro.rpi3dprinter.BuildConfig

inline fun log(lambda: () -> String) {
    if (BuildConfig.DEBUG) {
        Log.d("TAG", lambda())
    }
}