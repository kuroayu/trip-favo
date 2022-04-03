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
//    val access: String,
    val address1: String, //住所 必要
    val address2: String,
//    val dpPlanListUrl: String,
//    val faxNo: String,
    val hotelImageUrl: String, //ホテル写真URL 必要
    val hotelInformationUrl: String,  //ホテルURL 必要
//    val hotelKanaName: String,
//    val hotelMapImageUrl: String,
    val hotelMinCharge: Int, //最安値 必要
    val hotelName: String, //ホテル名 必要
//    val hotelNo: Int,
//    val hotelSpecial: String,
//    val hotelThumbnailUrl: String,
//    val latitude: Double,
//    val longitude: Double,
//    val nearestStation: String,
//    val parkingInformation: String,
//    val planListUrl: String,
//    val postalCode: String,
    val reviewAverage: Double, //星評価 必要
//    val reviewCount: Int,
//    val reviewUrl: String,
//    val roomImageUrl: String,
//    val roomThumbnailUrl: String,
//    val telephoneNo: String,
//    val userReview: String
)