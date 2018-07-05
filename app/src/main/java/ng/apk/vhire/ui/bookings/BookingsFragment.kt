package ng.apk.vhire.ui.bookings


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.dmoral.prefs.Prefs
import kotlinx.android.synthetic.main.fragment_bookings.*
import ng.apk.vhire.MainActivity


import ng.apk.vhire.R
import ng.apk.vhire.data.remote.api.Http
import ng.apk.vhire.models.Booking
import ng.apk.vhire.models.Session
import ng.apk.vhire.ui.base.BaseFragment
import ng.apk.vhire.ui.bookingdetails.BookingDetailsActivity
import ng.apk.vhire.ui.categories.CategoriesActivity
import ng.apk.vhire.ui.placebooking.PlaceBookingAdapter
import ng.apk.vhire.ui.signup.SignupActivity
import ng.apk.vhire.utils.RecyclerTouchListener


/**
 * A simple [Fragment] subclass.
 */
class BookingsFragment : BaseFragment(), BookingsContract.View {
    override fun checkIfLoggedInAndRedirect(): Boolean {

        return Prefs.with(context!!).contains("token")

    }

    override fun gotoSignup() {
        showToast("You need to be logged in to do this")

        val intent = Intent(activity, MainActivity::class.java)
        intent.putExtra("profile", true)
        startActivity(intent)

    }

    override fun updateBookings(bookings: List<Booking>) {

        this.bookings.addAll(bookings)
        this.mAdapter.notifyDataSetChanged()

    }

    override lateinit var presenter: BookingsContract.Presenter


    lateinit var bookings: ArrayList<Booking>
    lateinit var mAdapter: BookingsAdapter
    override fun initView() {
        bookings = ArrayList<Booking>()
        if (arguments!!["bookings"].toString().isEmpty()) {
            presenter.fetchListOfBookings()
        } else {
            val listType = object : TypeToken<List<Booking>>() {

            }.type
            bookings = Gson().fromJson(arguments!!["bookings"].toString(), listType)

        }
//        showToast(arguments!!["bookings"].toString())


        mAdapter = BookingsAdapter(bookings, context!!)

        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)


        recyclerView.addOnItemTouchListener(RecyclerTouchListener(
                activity,
                recyclerView,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {

                        val intent = Intent(activity, BookingDetailsActivity::class.java)
                        intent.putExtra("sessions", Gson().toJson(bookings[position].sessions))
//                        intent.putExtra("booking", Gson().toJson(bookings[position]))
                        startActivity(intent)
                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }

                }))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_bookings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BookingsPresenter(this, Http(context))

        presenter.start()

    }

    companion object {

        fun newInstance(bookings: String): BookingsFragment {

            val args = Bundle()
            args.putString("bookings", bookings)

            val fragment = BookingsFragment()
            fragment.arguments = args
            return fragment
        }
    }


}// Required empty public constructor
