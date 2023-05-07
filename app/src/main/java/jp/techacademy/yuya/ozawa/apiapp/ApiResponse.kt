package jp.techacademy.yuya.ozawa.apiapp
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "results")
    val results: Results
)

@JsonClass(generateAdapter = true)
data class Results(
    @Json(name = "shop")
    val shop: List<Shop>
)

@JsonClass(generateAdapter = true)
data class Shop(
    @Json(name = "address")
    val address: String,
    @Json(name = "coupon_urls")
    val couponUrls: CouponUrls,
    @Json(name = "id")
    val id: String,
    @Json(name = "logo_image")
    val logoImage: String,
    @Json(name = "name")
    val name: String
)



@JsonClass(generateAdapter = true)
data class CouponUrls(
    @Json(name = "pc")
    val pc: String,
    @Json(name = "sp")
    val sp: String
)

