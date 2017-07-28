package lt.andro.rpi3dprinter

import com.google.firebase.database.*
import lt.andro.rpi3printerremotecontrol.BasePresenter
import lt.andro.rpi3printerremotecontrol.BasePresenterImpl
import lt.andro.rpi3printerremotecontrol.MainView
import lt.andro.rpi3printerremotecontrol.log

interface MainPresenter : BasePresenter

interface LedThing {
    fun switchLed(isOn: Boolean)
    fun isLedOn(): Boolean
}

interface RelayThing {
    fun switchRelay(isOn: Boolean)
    fun isRelayOn(): Boolean
}

class MainPresenterImpl(val view: MainView) : BasePresenterImpl(), MainPresenter {

    override fun onAttach() {
        super.onAttach()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        val power = FirebaseDatabase.getInstance().getReference("power");
        val printer = power.child("printer")
        val listener = object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot?) {
                if (data != null) {
                    val typeIndicator = object : GenericTypeIndicator<Boolean>() {}
                    view.showPrinter(data.getValue(typeIndicator))
                }
            }

            override fun onCancelled(e: DatabaseError?) {
                log { e.toString() }
            }
        }
        printer.addValueEventListener(listener)
    }

    override fun onDetach() {
        super.onDetach()
        view.showLights(false)
    }
}