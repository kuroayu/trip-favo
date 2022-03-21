data class RakutenHotelResult(
    val hotels: List<Hotel>
)

data class Hotel(
    val hotel: List<HotelX>
)

data class HotelX(
    val hotelBasicInfo: HotelBasicInfo
)

data class HotelBasicInfo(
    val access: String,
    val address1: String,
    val address2: String,
    val dpPlanListUrl: String,
    val faxNo: String,
    val hotelImageUrl: String,
    val hotelInformationUrl: String,
    val hotelKanaName: String,
    val hotelMapImageUrl: String,
    val hotelMinCharge: Int,
    val hotelName: String,
    val hotelNo: Int,
    val hotelSpecial: String,
    val hotelThumbnailUrl: String,
    val latitude: Double,
    val longitude: Double,
    val nearestStation: Any,
    val parkingInformation: String,
    val planListUrl: String,
    val postalCode: String,
    val reviewAverage: Double,
    val reviewCount: Int,
    val reviewUrl: String,
    val roomImageUrl: Any,
    val roomThumbnailUrl: Any,
    val telephoneNo: String,
    val userReview: String
)