package lt.andro.rpi3dprinter

class AndroidThingsUtils {
    fun isThingsAvailable(): Boolean {
        try {
            Class.forName("PeripheralManagerService")
            return true
        } catch (e: ClassNotFoundException) {
            return false
        }
    }
}