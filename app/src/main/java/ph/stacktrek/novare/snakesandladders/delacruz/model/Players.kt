package ph.stacktrek.novare.snakesandladders.delacruz.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

open class Players(var name: String?) : Parcelable {
    lateinit var userID: String
    var imagePath:String = ""
    var position: Int = -1;
    var lastPosition: Int = 0;

    constructor(parcel: Parcel) : this(parcel.readString()) {
        imagePath = parcel.readString().toString()
        position = parcel.readInt()
        lastPosition = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(imagePath)
        parcel.writeInt(position)
        parcel.writeInt(lastPosition)
    }

    override fun describeContents(): Int {
        return 0
    }

    @SuppressLint("ParcelCreator")
    companion object CREATOR : Parcelable.Creator<Players> {
        override fun createFromParcel(parcel: Parcel): Players {
            return Players(parcel)
        }

        override fun newArray(size: Int): Array<Players?> {
            return arrayOfNulls(size)
        }
    }
}






