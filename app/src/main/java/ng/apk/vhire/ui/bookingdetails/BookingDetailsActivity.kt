package ng.apk.vhire.ui.bookingdetails

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_booking_details.*
import ng.apk.vhire.R
import ng.apk.vhire.data.remote.api.Http
import ng.apk.vhire.models.Booking
import ng.apk.vhire.models.Session
import ng.apk.vhire.ui.base.BaseActivity
import ng.apk.vhire.utils.RecyclerTouchListener
import android.content.DialogInterface
import com.cocosw.bottomsheet.BottomSheet


class BookingDetailsActivity : BaseActivity(), BookingDetailsContract.View {
    override lateinit var presenter: BookingDetailsContract.Presenter


    lateinit var mAdapter: BookingDetailsAdapter
    lateinit var sessions: List<Session>
    //    lateinit var booking: Booking: List<Session>
    override fun initView() {

        mAdapter = BookingDetailsAdapter(sessions, this)

        sessionsRecyler.adapter = mAdapter
        sessionsRecyler.layoutManager = LinearLayoutManager(this)

        sessionsRecyler.addOnItemTouchListener(RecyclerTouchListener(
                this@BookingDetailsActivity,
                sessionsRecyler,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {


                        BottomSheet.Builder(this@BookingDetailsActivity).title("Options").sheet(R.menu.sessions_menu).listener { dialog, which ->
                            when (which) {
                                R.id.submit -> {
                                    presenter.submitSession(sessions[position])
                                    sessions[position].status = 3
                                    mAdapter.notifyDataSetChanged()
                                }
                                R.id.cancel -> {
                                    presenter.cancelSession(sessions[position])
                                    sessions[position].status = 5
                                    mAdapter.notifyDataSetChanged()
                                }
                                R.id.start -> showToast("clicked start")
                            }
                        }.show()
                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }

                }))


    }

    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)
        toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Booking Sessions"
        val bundle = intent.extras
        if (bundle != null) {
            val listType = object : TypeToken<List<Session>>() {

            }.type
            showToast(bundle["sessions"].toString())
            sessions = Gson().fromJson(bundle.getString("sessions"), listType)
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        BookingDetailsPresenter(this, Http(this))

        presenter.start()
    }

//    override fun onNavigateUp(): Boolean {
//        finish()
//        return super.onNavigateUp()
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sessions_info, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.sessions_info -> showToast("Info")
        }
        return super.onOptionsItemSelected(item)
    }
}
