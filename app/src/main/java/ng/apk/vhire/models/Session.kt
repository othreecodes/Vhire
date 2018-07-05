package ng.apk.vhire.models

data class Session(var id:Int?=null, val start:String, val time:String, val no_of_hours:Float, val booking: Booking, var status:Int=4){
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