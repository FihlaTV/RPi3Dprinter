package lt.andro.rpi3dprinter

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity(), MainView {

    private val presenter: MainPresenter by lazy { MainPresenterImpl(this) }
    private val things: ThingsExecutor by lazy { ThingsExecutorImpl() }

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

    override fun switchLed(isOn: Boolean) {
        things.switchLed(isOn)
    }

    override fun isLedOn(): Boolean {
        return things.isLedOn()
    }
}

