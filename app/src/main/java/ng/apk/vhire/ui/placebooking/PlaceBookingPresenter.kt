package ng.apk.vhire.ui.placebooking

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import ng.apk.vhire.data.remote.api.Http
import ng.apk.vhire.models.Session
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlaceBookingPresenter(val mView: PlaceBookingContract.View, val http: Http) : PlaceBookingContract.Presenter {
    override fun placeBooking(sesions: List<Session>) {

        if (sesions.size < 1) {
            mView.showToast("You need to add at least one time for this booking", "error")
            return

        }
        mView.showLoadingDialog("Placing booking...")
        var data = Gson().toJson(sesions)
        this.http.makeBooking(data)
                .enqueue(object : Callback<JsonArray> {
                    override fun onFailure(call: Call<JsonArray>?, t: Throwable?) {
                        mView.hideLoadingDialog { }

                        mView.showToast(t.toString())

                        mView.onErrorInConnection {
                            this@PlaceBookingPresenter.placeBooking(sesions)
                        }

                    }

                    override fun onResponse(call: Call<JsonArray>?, response: Response<JsonArray>?) {

                        mView.hideLoadingDialog {
                            mView.showToast("Your booking has been placed")
                            mView.showBookingsPage(response!!.body().toString())
                        }

                    }

                })

    }

    override fun addToBookingList() {


        mView.showDatePicker {
            mView.showTimePicker {
                mView.addToList()
            }
        }


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