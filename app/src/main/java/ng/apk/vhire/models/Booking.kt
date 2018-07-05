package ng.apk.vhire.models

data class Booking(
        var id: Int? = null,
        var service: Int?,
        var service_extras: List<ServiceExtra>?,
        var sessions: List<Session>? = null,
        var name: String? = null,
        val start: String? = null,
        val price: Float? = null,
        val status: Int = 4) {

    fun getStatus(): String {
        return when (status) {
            1 -> "not started"
            2 -> "started"
            3 -> "completed"
            4 -> "pending"
            5 -> "cancelled"
            else -> "unknown"
        }

    }

}