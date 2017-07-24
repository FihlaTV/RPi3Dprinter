package lt.andro.rpi3dprinter

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService

class MainActivity : Activity(), MainView {

    private val TAG = "MainActivity"
    private val LED_GPIO = "BCM6"
    val presenter: MainPresenter by lazy { MainPresenterImpl(this) }

    private var mLedGpio: Gpio = PeripheralManagerService().openGpio(LED_GPIO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "Configuring GPIO pins")
        mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach()
    }

    override fun onPause() {
        super.onPause()
        presenter.onDetach()
    }

    override fun switchLed(isOn: Boolean) {
        mLedGpio.value = isOn
    }

    override fun isLedOn(): Boolean {
        return mLedGpio.value
    }
}

