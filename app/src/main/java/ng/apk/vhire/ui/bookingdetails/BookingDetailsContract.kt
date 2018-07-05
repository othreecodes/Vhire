package ng.apk.vhire.ui.bookingdetails


import ng.apk.vhire.models.Session
import ng.apk.vhire.ui.base.BasePresenter
import ng.apk.vhire.ui.base.BaseView

interface BookingDetailsContract {


    public interface View : BaseView<Presenter> {


    }

    public interface Presenter : BasePresenter {
        fun cancelSession(id: Session)
        fun submitSession(id: Session)
    }
}