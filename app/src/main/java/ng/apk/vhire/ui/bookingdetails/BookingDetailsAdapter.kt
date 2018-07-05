package ng.apk.vhire.ui.bookingdetails

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotterknife.bindView
import ng.apk.vhire.R
import ng.apk.vhire.models.Session

class BookingDetailsAdapter(val sessions: List<Session>, val context: Context) : RecyclerView.Adapter<BookingDetailsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.content_booking_list, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sessions.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val session = sessions[position]
        holder.image.setImageResource(R.drawable.ic_bookmark_black_24dp)
        holder.name.text = "${session.start} - ${session.time} (${session.getStatus()})"
        holder.price.text = "₦ ${session.booking.price} per hour"
        holder.sub.text = "${session.no_of_hours} hours"

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView by bindView(R.id.nameTextView)
        val sub: TextView by bindView(R.id.devTextView)
        val price: TextView by bindView(R.id.ratingTextView)
        val image: ImageView by bindView(R.id.imageView)
    }
}