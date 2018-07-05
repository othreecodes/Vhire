package ng.apk.vhire.ui.search

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
import ng.apk.vhire.models.JobCategory


class SearchAdapter(private val context: Context?, private val categories: List<JobCategory>?) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.content_category_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories!![position]

        Glide.with(context!!).load(category.image).into(holder.singleImageView)
        holder.textView.text = category.name
        holder.textView.isSelected = true


    }

    override fun getItemCount(): Int {
        return categories!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val singleImageView: ImageView by bindView(R.id.single_content)


        val textView: TextView by bindView(R.id.single_content_title)

    }
}
