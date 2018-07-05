package ng.apk.vhire.models

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

class JobCategory protected constructor(`in`: Parcel) : Parcelable {

    var id: Int
    var name: String
    var slug: String
    var image: String

    @SerializedName("parent_category")
    var parentCategory: JobCategory
    var description: String


    init {
        id = `in`.readInt()
        name = `in`.readString()
        slug = `in`.readString()
        image = `in`.readString()
        parentCategory = `in`.readParcelable(JobCategory::class.java.classLoader)
        description = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(slug)
        dest.writeString(image)
        dest.writeParcelable(parentCategory, flags)
        dest.writeString(description)
    }

    companion object {

        val CREATOR: Parcelable.Creator<JobCategory> = object : Parcelable.Creator<JobCategory> {
            override fun createFromParcel(`in`: Parcel): JobCategory {
                return JobCategory(`in`)
            }

            override fun newArray(size: Int): Array<JobCategory?> {
                return arrayOfNulls(size)
            }
        }
    }

    fun getNewCategory():String{
        return "Hello"
    }
}
