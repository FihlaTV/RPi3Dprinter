package lt.andro.rpi3dprinter

import com.google.firebase.database.*
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
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        val power = FirebaseDatabase.getInstance().getReference("power");
        val printer = power.child("printer")
        val listener = object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot?) {
                if (data != null) {
                    val typeIndicator = object : GenericTypeIndicator<Boolean>() {}
                    view.switchRelay(data.getValue(typeIndicator))
                }
            }

            override fun onCancelled(e: DatabaseError?) {
                log { e.toString() }
            }
        }
        printer.addValueEventListener(listener)
    }

    private fun startHeartBeating() {
        println("Starting heart beating.")
        val subscribe = Observable
                .interval(3700, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .doOnNext {
                    val isOn = !view.isLedOn();

                    println("Blink: $isOn")
                    view.switchLed(isOn)
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
