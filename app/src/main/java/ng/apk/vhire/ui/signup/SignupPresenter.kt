package ng.apk.vhire.ui.signup

import com.google.gson.JsonObject
import es.dmoral.prefs.Prefs
import ng.apk.vhire.data.remote.api.Http
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignupPresenter(val mView: SignupContract.View, val http: Http) : SignupContract.Presenter {
    override fun signup(data: JsonObject) {

        mView.showLoadingDialog("Please wait while we sign you up...")

        this.http.signup(data).enqueue(object : Callback<JsonObject> {


            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                mView.hideLoadingDialog {
                    if (response!!.isSuccessful) {
                        mView.showToast(response.body().toString(), "info")
                        mView.onSuccessfulsignup(response.body())
                      } else {
                        mView.showToast(response.errorBody()!!.string(), "error")
                    }
                }
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
               mView.hideLoadingDialog {
                   mView.showToast(t.toString(), "error")
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