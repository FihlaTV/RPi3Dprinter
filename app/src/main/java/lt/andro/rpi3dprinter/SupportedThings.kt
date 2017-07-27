package lt.andro.rpi3dprinter

import android.util.Log
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService

interface SupportedThings : LedThing, RelayThing

class ThingsExecutorMock(val messageView: MessageView) : SupportedThings {
    private val TAG = this.javaClass.simpleName
    private var ledValue: Boolean = false
    private var relayValue: Boolean = false

    init {
        Log.i(TAG, "Building Mocked Things Executor")
    }

    override fun switchLed(isOn: Boolean) {
        val msg = "Mocked LED is " + if (isOn) "on" else "off";
        log { msg }
        ledValue = isOn
        messageView.showMessage(msg)
    }

    override fun isLedOn(): Boolean {
        return ledValue
    }

    override fun switchRelay(isOn: Boolean) {
        val txt = if (isOn) "on" else "off"
        val msg = "Mocked RELAY is " + txt;
        log { msg }
        relayValue = isOn
    }

    override fun isRelayOn(): Boolean {
        return relayValue
    }
}

interface MessageView {
    fun showMessage(msg: String)
}

class ThingsExecutorImpl(val messageView: MessageView) : SupportedThings {
    private val TAG = this.javaClass.simpleName
    private val LED_GPIO_PIN = "BCM6"
    private val RELAY_GPIO_PIN = "BCM5"

    private val ledGpio: Gpio by lazy { PeripheralManagerService().openGpio(LED_GPIO_PIN) }
    private val relayGpio: Gpio by lazy { PeripheralManagerService().openGpio(RELAY_GPIO_PIN) }

    init {
        Log.i(TAG, "Configuring GPIO pins")
        ledGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        relayGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
    }

    override fun switchLed(isOn: Boolean) {
        ledGpio.value = isOn

        val msg = "Real LED is " + if (isOn) "on" else "off";
        log { msg }
        messageView.showMessage(msg)
    }

    override fun isLedOn(): Boolean {
        return ledGpio.value
    }

    override fun switchRelay(isOn: Boolean) {
        relayGpio.value = isOn
    }

    override fun isRelayOn(): Boolean {
        return relayGpio.value
    }

}