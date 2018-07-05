package ng.apk.vhire.ui.categories

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotterknife.bindView
import ng.apk.vhire.R
import ng.apk.vhire.models.Service


class ServicesAdapter(val context: Context?) : RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    var services:List<Service>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.content_services, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (services==null){
            return 0
        }
        return services!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = services!![position]

        holder.titleTextView.text = service.name
        holder.contentTextView.text = service.description

        Glide.with( context!!).load(service.image).into(holder.singleImageView)
    }


    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val singleImageView: ImageView by bindView(R.id.imageView)

        val titleTextView: TextView by bindView(R.id.title)
        val contentTextView: TextView by bindView(R.id.content)

    }
}