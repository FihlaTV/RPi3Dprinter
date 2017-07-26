package lt.andro.rpi3dprinter

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), MainView, MessageView {
    override fun showMessage(msg: String) {
        mainMessageView.text = msg
    }

    private val presenter: MainPresenter by lazy { MainPresenterImpl(this) }
    private val things: ThingsExecutor by lazy {
        if (AndroidThingsUtils().isThingsAvailable())
            ThingsExecutorImpl()
        else
            ThingsExecutorMock(this)
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

    override fun switchLed(isOn: Boolean) {
        things.switchLed(isOn)
    }

    override fun isLedOn(): Boolean {
        return things.isLedOn()
    }
}

