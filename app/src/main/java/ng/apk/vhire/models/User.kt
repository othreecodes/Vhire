package ng.apk.vhire.models

import com.google.gson.annotations.SerializedName


data class User(
        @SerializedName("first_name") val firstName:String,
        @SerializedName("last_name") val lastName:String,
        val email:String,
        val username:String,
        val role:String,
        @SerializedName("phone_number") val phoneNumber:String
) {

}