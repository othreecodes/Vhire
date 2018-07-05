package ng.apk.vhire.ui.placebooking

import ng.apk.vhire.models.Session
import ng.apk.vhire.ui.base.BasePresenter
import ng.apk.vhire.ui.base.BaseView


interface PlaceBookingContract {

    interface View : BaseView<Presenter> {
        fun showDatePicker(callback: () -> Unit)
        fun showTimePicker(callback: () -> Unit)
        fun showExtrasPicker()
        fun addToList()



        fun showBookingsPage(bookings: String)
    }
    interface Presenter : BasePresenter {
        fun addToBookingList()
        fun placeBooking(sesions: List<Session>)
    }
}