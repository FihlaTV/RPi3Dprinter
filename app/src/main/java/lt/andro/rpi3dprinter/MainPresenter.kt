package lt.andro.rpi3dprinter
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

interface MainPresenter : BasePresenter

interface LedThing {
    fun switchLed(isOn: Boolean)
    fun isLedOn(): Boolean
}

interface RelayThing {
    fun switchRelay(isOn: Boolean)
    fun isRelayOn(): Boolean
}

class MainPresenterImpl(val view: SupportedThings) : BasePresenterImpl(), MainPresenter {
    override fun onAttach() {
        super.onAttach()
        startHeartBeating()
    }

    private fun startHeartBeating() {
        println("Starting heart beating.")
        val subscribe = Observable
                .interval(700, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .doOnNext {
                    val isOn = !view.isLedOn();

                    println("Blink: $isOn")
                    view.switchLed(isOn)
                    view.switchRelay(isOn)
                }
                .doOnError { it.printStackTrace() }
                .doOnCompleted { error("Should never complete") }
                .subscribe()
        add(subscribe)
        view.switchLed(true)
    }

    override fun onDetach() {
        super.onDetach()
        view.switchLed(false)
    }
}
