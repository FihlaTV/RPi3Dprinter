package lt.andro.rpi3printerremotecontrol

import com.google.firebase.database.*


interface MainPresenter : BasePresenter {
    fun lightsSwitch(isOn: Boolean)
    fun printerSwitch(isOn: Boolean)
}

class MainPresenterImpl(val view: MainView) : BasePresenterImpl(), MainPresenter {
    override fun lightsSwitch(isOn: Boolean) {
        log { "On lightsSwitch " + isOn }
        val update = HashMap<String, Any>()
        update.put("lights", isOn)
        getPowerSwitch().updateChildren(update)
    }

    override fun printerSwitch(isOn: Boolean) {
        log { "On printerSwitch " + isOn }
        val update = HashMap<String, Any>()
        update.put("printer", isOn)
        getPowerSwitch().updateChildren(update)
    }

    override fun onAttach() {
        super.onAttach()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        log { "onAttach: register printer onDataChanged listener" }
        val printer = getPowerSwitch().child("printer")
        val listener = object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot?) {
                log { "onDataChanged " + data }
                if (data != null) {
                    val typeIndicator = object : GenericTypeIndicator<Boolean>() {}
                    val isOn = data.getValue(typeIndicator)
                    log { "got new printer value from Firebase" + isOn }
                    view.showPrinter(isOn)
                }
            }

            override fun onCancelled(e: DatabaseError?) {
                log { e.toString() }
            }
        }
        printer.addValueEventListener(listener)
    }

    private fun getPowerSwitch() = FirebaseDatabase.getInstance().getReference("power")

    override fun onDetach() {
        super.onDetach()
        view.showLights(false)
    }
}