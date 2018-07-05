package ng.apk.vhire.ui.bookings

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
//import kotlinx.android.synthetic.main.content_booking_list.view.*
import kotterknife.bindView
import ng.apk.vhire.R
import ng.apk.vhire.models.Booking

class BookingsAdapter(val bookings: List<Booking>, val context: Context) : RecyclerView.Adapter<BookingsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.content_booking_list, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookings.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val booking = bookings[position]

        holder.name.text = booking.name
        holder.price.text = booking.price.toString()
        holder.sub.text = "${booking.start} (${booking.getStatus()})"

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView by bindView(R.id.nameTextView)
        val sub: TextView by bindView(R.id.devTextView)
        val price: TextView by bindView(R.id.ratingTextView)


    }
}