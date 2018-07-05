package ng.apk.vhire.models

import android.os.Parcel
import android.os.Parcelable


data class ServiceExtra(
        val name: String,
        val description: String,
        val price : Int,
        val selected:Boolean,
        val unit:String,
        val quantity:Int
):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(price)
        parcel.writeByte(if (selected) 1 else 0)
        parcel.writeString(unit)
        parcel.writeInt(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ServiceExtra> {
        override fun createFromParcel(parcel: Parcel): ServiceExtra {
            return ServiceExtra(parcel)
        }

        override fun newArray(size: Int): Array<ServiceExtra?> {
            return arrayOfNulls(size)
        }
    }
}
