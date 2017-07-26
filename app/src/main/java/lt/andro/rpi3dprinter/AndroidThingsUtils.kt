package lt.andro.rpi3dprinter

class AndroidThingsUtils {
    fun isAvailable(): Boolean {
        try {
            val forName = Class.forName("PeripheralManagerService")
            return true
        } catch (e: ClassNotFoundException) {
            return false
        }
    }
}