package ng.apk.vhire.ui.servicedetail

import ng.apk.vhire.data.remote.api.Http
import ng.apk.vhire.models.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ServiceDetailPresenter(val mView:ServiceDetailContract.View,val http: Http):ServiceDetailContract.Presenter {
    override fun placeSingleBooking(serviceid: Int) {

    }

    override fun fetchServiceExtras(serviceid: Int) {
        mView.showLoadingDialog("Fetching service extras")

        http.getService(id = serviceid).enqueue(object : Callback<Service>{
            override fun onFailure(call: Call<Service>?, t: Throwable?) {

                mView.hideLoadingDialog {
                    mView.onErrorInConnection {
                        fetchServiceExtras(serviceid)
                    }
                }
            }

            override fun onResponse(call: Call<Service>?, response: Response<Service>?) {

                mView.hideLoadingDialog {

                    mView.updateExtrasList(response!!.body())
                }
             }

        })


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