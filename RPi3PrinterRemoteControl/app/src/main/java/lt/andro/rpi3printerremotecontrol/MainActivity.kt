package lt.andro.rpi3printerremotecontrol

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Switch
import lt.andro.rpi3dprinter.MainPresenter
import lt.andro.rpi3dprinter.MainPresenterImpl

class MainActivity : AppCompatActivity(), MainView {
    private val presenter: MainPresenter by lazy { MainPresenterImpl(this) }

    override fun showPrinter(isOn: Boolean) {
        (findViewById(R.id.main_printer_switch) as Switch).isChecked = isOn
    }

    override fun showLights(isOn: Boolean) {
        (findViewById(R.id.main_lights_switch) as Switch).isChecked = isOn
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach()
    }

    override fun onPause() {
        super.onPause()
        presenter.onDetach()
    }
}

interface MainView {
    fun showPrinter(isOn: Boolean)
    fun showLights(isOn: Boolean)
}
