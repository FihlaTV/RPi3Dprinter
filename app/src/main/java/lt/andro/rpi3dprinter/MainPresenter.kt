package lt.andro.rpi3dprinter

import com.google.firebase.database.*

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
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        getPowerDataChild()
                .child("printer")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(data: DataSnapshot?) {
                        if (data != null) {
                            val typeIndicator = object : GenericTypeIndicator<Boolean>() {}
                            view.switchRelay(data.getValue(typeIndicator))
                        }
                    }

                    override fun onCancelled(e: DatabaseError?) {
                        log { e.toString() }
                    }
                })

        getPowerDataChild()
                .child("lights")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(data: DataSnapshot?) {
                        if (data != null) {
                            val typeIndicator = object : GenericTypeIndicator<Boolean>() {}
                            view.switchLed(data.getValue(typeIndicator))
                        }
                    }

                    override fun onCancelled(e: DatabaseError?) {
                        log { e.toString() }
                    }
                })
    }

    private fun getPowerDataChild() = FirebaseDatabase.getInstance().getReference("power")

    override fun onDetach() {
        super.onDetach()
        view.switchLed(false)
    }
}
