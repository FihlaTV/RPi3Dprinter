package lt.andro.rpi3dprinter

class AndroidThingsUtils {
    fun isThingsAvailable(): Boolean {
        return true
        try {
            Class.forName("PeripheralManagerService")
            return true
        } catch (e: ClassNotFoundException) {
            return false
        }
    }
}