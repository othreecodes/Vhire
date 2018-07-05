package ng.apk.vhire.ui.bookingdetails

import ng.apk.vhire.data.remote.api.Http
import ng.apk.vhire.models.Session
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingDetailsPresenter(val mView:BookingDetailsContract.View, val http: Http):BookingDetailsContract.Presenter{
    override fun cancelSession(id: Session) {

        mView.showLoadingDialog("Canceling session please wait..")

        http.cancelSession(id.id!!).enqueue(object :Callback<Session>{
            override fun onFailure(call: Call<Session>?, t: Throwable?) {

                mView.hideLoadingDialog {

                    mView.showToast(t.toString())

                    mView.onErrorInConnection {
                        cancelSession(id)
                    }
                }

            }

            override fun onResponse(call: Call<Session>?, response: Response<Session>?) {

                mView.hideLoadingDialog {
                    mView.showToast("Successful")
                }


            }

        })

    }

    override fun submitSession(id: Session) {

     }

    init {
        mView.presenter = this
    }
    override fun start() {
        mView.initView()
    }

    override fun finalize() {

     }

}