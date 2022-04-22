import com.kuro.trip_favo.data.database.FavoriteHotel
import kotlinx.serialization.Serializable

@Serializable
data class RakutenHotelResult(
    val hotels: List<Hotel>
)

@Serializable
data class Hotel(
    val hotel: List<HotelX>
)

@Serializable
data class HotelX(
    val hotelBasicInfo: HotelBasicInfo
)

@Serializable
data class HotelBasicInfo(
    val address1: String, //住所 必要
    val address2: String,
    val hotelImageUrl: String, //ホテル写真URL 必要
    val hotelInformationUrl: String,  //ホテルURL 必要
    val hotelMinCharge: Int, //最安値 必要
    val hotelName: String, //ホテル名 必要
    val hotelNo: Int,
    val reviewAverage: Double, //星評価 必要
) {
    fun toFavoriteHotel(date: Long, onsen: Int): FavoriteHotel {
        return FavoriteHotel(
            hotelNo,
            date,
            onsen,
            hotelName,
            hotelImageUrl,
            hotelInformationUrl,
            address1,
            address2,
            reviewAverage,
            hotelMinCharge.toDouble()
        )
    }
}