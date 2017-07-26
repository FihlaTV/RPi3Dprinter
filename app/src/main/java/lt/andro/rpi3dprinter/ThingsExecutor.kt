package lt.andro.rpi3dprinter

import android.util.Log
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService

interface ThingsExecutor : MainView {

}

class ThingsExecutorImpl() : ThingsExecutor {
    private val TAG = this.javaClass.simpleName
    private val LED_GPIO = "BCM6"

    init {
        Log.i(TAG, "Configuring GPIO pins")
        mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
    }

    private var mLedGpio: Gpio = PeripheralManagerService().openGpio(LED_GPIO)

    override fun switchLed(isOn: Boolean) {
        mLedGpio.value = isOn
    }

    override fun isLedOn(): Boolean {
        return mLedGpio.value
    }

}