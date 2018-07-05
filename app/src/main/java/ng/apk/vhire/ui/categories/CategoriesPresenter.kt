package ng.apk.vhire.ui.categories

import ng.apk.vhire.data.remote.api.Http
import ng.apk.vhire.models.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoriesPresenter(private val mView: CategoriesContract.View, val http: Http) : CategoriesContract.Presenter {

    init {
        mView.presenter = this
    }

    override fun start() {

        this.mView.initView()
    }

    override fun finalize() {

    }



    override fun fetchServices(id: Int) {
        mView.showLoadingDialog("Fetching available services")
        http.services(id).enqueue(object : Callback<List<Service>> {
            override fun onFailure(call: Call<List<Service>>?, t: Throwable?) {
                mView.hideLoadingDialog {
                    mView.onErrorInConnection({
                        fetchServices(id)
                    })

                }

            }

            override fun onResponse(call: Call<List<Service>>?, response: Response<List<Service>>?) {
                mView.hideLoadingDialog {
                    if(response!!.isSuccessful){

                        mView.updateServiceList(response.body()!!)
                    }
                }
            }

        })
    }
}
