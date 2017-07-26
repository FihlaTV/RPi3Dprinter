package lt.andro.rpi3dprinter

import android.util.Log
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService

interface ThingsExecutor : MainView

class ThingsExecutorMock : ThingsExecutor {
    private var value: Boolean = false

    override fun switchLed(isOn: Boolean) {
        val txt = if(isOn) "on" else "off"
        log { "Led is " + txt }
        value = isOn
    }

    override fun isLedOn(): Boolean {
        return value
    }
}

class ThingsExecutorImpl : ThingsExecutor {
    private val TAG = this.javaClass.simpleName
    private val LED_GPIO = "BCM6"

    private val mLedGpio: Gpio by lazy { PeripheralManagerService().openGpio(LED_GPIO) }

    init {
        Log.i(TAG, "Configuring GPIO pins")
        mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
    }

    override fun switchLed(isOn: Boolean) {
        mLedGpio.value = isOn
    }

    override fun isLedOn(): Boolean {
        return mLedGpio.value
    }

}