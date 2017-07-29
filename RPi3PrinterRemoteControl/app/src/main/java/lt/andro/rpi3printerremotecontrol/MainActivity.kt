package lt.andro.rpi3printerremotecontrol

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {
    private val presenter: MainPresenter by lazy { MainPresenterImpl(this) }

    override fun showPrinter(isOn: Boolean) {
        main_printer_switch.isChecked = isOn
    }

    override fun showLights(isOn: Boolean) {
        main_lights_switch.isChecked = isOn
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_lights_switch.setOnClickListener {  _ -> presenter.lightsSwitch(main_lights_switch.isChecked)}
        main_printer_switch.setOnClickListener {  _ -> presenter.printerSwitch(main_printer_switch.isChecked)}
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
