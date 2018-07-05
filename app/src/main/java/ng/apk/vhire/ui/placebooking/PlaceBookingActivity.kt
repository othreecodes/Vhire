package ng.apk.vhire.ui.placebooking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.widget.DatePicker
import android.widget.TimePicker
import kotlinx.android.synthetic.main.activity_place_booking.*

import ng.apk.vhire.R
import ng.apk.vhire.data.remote.api.Http
import ng.apk.vhire.models.Booking
import ng.apk.vhire.models.Service
import ng.apk.vhire.models.Session
import ng.apk.vhire.ui.base.BaseActivity
import java.util.*
import com.afollestad.materialdialogs.MaterialDialog
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import es.dmoral.prefs.Prefs
import kotterknife.bindView
import ng.apk.vhire.MainActivity
import ng.apk.vhire.ui.signup.SignupActivity
import kotlin.collections.ArrayList


class PlaceBookingActivity : BaseActivity(), PlaceBookingContract.View, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    override fun showBookingsPage(bookings: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("bookings", bookings)
        startActivity(intent)
        finish()

    }



    override fun addToList() {
        MaterialDialog.Builder(this)
                .title("How long should this session last?")

                .content("How many hours?")
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .input("Enter number", "", MaterialDialog.InputCallback { dialog, input ->
                    // Do something

                    var session = Session(
                            null, dateString, timeString, input.toString().toFloat(), booking
                    )

                    sessions.add(session)
                    mAdapter.notifyDataSetChanged()

                }).show()


    }

    override fun showExtrasPicker() {
//        MaterialDialog.Builder(this)
//                .title("Do you want to add any extras?")
//                .items()

        presenter.placeBooking(sessions)

    }


    override fun showDatePicker(callback: () -> Unit) {

        val dp = DatePickerDialog(this, this, this.day.get(Calendar.YEAR), this.day.get(Calendar.MONTH), this.day.get(Calendar.DAY_OF_MONTH))
        dp.setTitle("select a day")
        dp.show()
        dp.setOnDismissListener({
            callback()
        })


    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {


        this.day.set(Calendar.YEAR, year)
        this.day.set(Calendar.MONTH, month)
        this.day.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        dateString = "$year-$month-$dayOfMonth"
    }

    override fun showTimePicker(callback: () -> Unit) {
        val tp = TimePickerDialog(this, this, this.time.get(Calendar.HOUR), this.time.get(Calendar.MINUTE), true)
        tp.setTitle("When do you want this Session to start")
        tp.show()
        showToast("When should this session start")

        tp.setOnDismissListener({
            callback()
        })

    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        this.time.set(Calendar.HOUR, hourOfDay)
        this.time.set(Calendar.MINUTE, minute)

        timeString = "$hourOfDay:$minute"

    }


    lateinit var service: Service
    override lateinit var presenter: PlaceBookingContract.Presenter
    val day: Calendar = Calendar.getInstance()
    val time: Calendar = Calendar.getInstance()
    var dateString = "yyyy-MM-dd"
    var timeString = "HH:MM:SS"
    lateinit var booking: Booking
    lateinit var sessions: ArrayList<Session>
    lateinit var mAdapter: PlaceBookingAdapter


    val fabButton: FloatingActionButton by bindView(R.id.fab)
    override fun initView() {
        booking = Booking(null, null, null)
        booking.service = service.id
        sessions = ArrayList<Session>()


        fabButton.setOnClickListener {
            showToast("Choose Date for service")

            presenter.addToBookingList()
        }

        mAdapter = PlaceBookingAdapter(sessions)

        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_booking)
        PlaceBookingPresenter(this, http = Http(this))

        val bundle = intent.extras

        if (bundle != null) {
            service = bundle.getParcelable<Service>("service") as Service
         }
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Place booking for ${service.name}"

        presenter.start()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.savesessions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.save_sessions -> showExtrasPicker()
        }
        return super.onOptionsItemSelected(item)
    }

}
