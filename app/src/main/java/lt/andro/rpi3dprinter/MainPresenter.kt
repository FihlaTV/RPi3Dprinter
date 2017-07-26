package lt.andro.rpi3dprinter

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


interface MainPresenter : BasePresenter {
}

interface MainView {
    fun switchLed(isOn: Boolean)
    fun isLedOn(): Boolean
}

class MainPresenterImpl(val view: MainView) : MainPresenter {
    override fun onAttach() {
        startHeartBeating()
    }

    private fun startHeartBeating() {
        println("Starting heart beating.")
        Observable
                .interval(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .doOnNext {
                    val isOn =!view.isLedOn();

                    println("Blink: $isOn")
                    view.switchLed(isOn)
                }
                .doOnError { it.printStackTrace() }
                .doOnCompleted { error("Should never complete") }
                .subscribe()
        view.switchLed(true)
    }

    override fun onDetach() {
        view.switchLed(false)
    }
}
