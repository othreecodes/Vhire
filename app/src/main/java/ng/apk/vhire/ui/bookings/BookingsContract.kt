package ng.apk.vhire.ui.bookings

import com.google.gson.JsonArray
import ng.apk.vhire.models.Booking
import ng.apk.vhire.ui.base.BasePresenter
import ng.apk.vhire.ui.base.BaseView
import retrofit2.Call

interface BookingsContract{

    interface View : BaseView<Presenter> {
        fun updateBookings(bookings:List<Booking>)
        fun checkIfLoggedInAndRedirect():Boolean
        fun gotoSignup()
    }

    interface Presenter : BasePresenter {
        fun fetchListOfBookings()
    }

}