package ng.apk.vhire.ui.bookings

import com.google.gson.Gson
import com.google.gson.JsonArray
import ng.apk.vhire.data.remote.api.Http
import ng.apk.vhire.models.Booking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.reflect.TypeToken


class BookingsPresenter(val mView: BookingsContract.View, val http: Http) : BookingsContract.Presenter {
    override fun fetchListOfBookings() {
        mView.showLoadingDialog("fetching bookings...")

        http.bookings.enqueue(object : Callback<JsonArray> {
            override fun onFailure(call: Call<JsonArray>?, t: Throwable?) {

                mView.hideLoadingDialog {
                    mView.onErrorInConnection {
                        fetchListOfBookings()
                    }
                }
            }

            override fun onResponse(call: Call<JsonArray>?, response: Response<JsonArray>?) {

                mView.hideLoadingDialog {
                    val listType = object : TypeToken<List<Booking>>() {

                    }.type
                    var bookings: List<Booking> = Gson().fromJson(response!!.body(), listType)

                    mView.updateBookings(bookings)
                }


            }

        })

    }

    init {
        mView.presenter = this
    }

    override fun start() {
        if (mView.checkIfLoggedInAndRedirect()) {

            mView.initView()
        } else {
            mView.gotoSignup()
        }

    }

    override fun finalize() {


    }

}