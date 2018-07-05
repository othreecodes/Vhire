package ng.apk.vhire.ui.servicedetail

import ng.apk.vhire.models.Service
import ng.apk.vhire.ui.base.BasePresenter
import ng.apk.vhire.ui.base.BaseView


interface ServiceDetailContract {

    interface View : BaseView<Presenter> {
        fun updateExtrasList(service:Service?)
        fun checkIfLoggedInAndRedirect():Boolean
    }

    interface Presenter : BasePresenter{
        fun placeSingleBooking(serviceid:Int)
        fun fetchServiceExtras(serviceid: Int)
    }
}