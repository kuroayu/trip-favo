package com.kuro.trip_favo.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RakutenAreaResult(
    val areaClasses: Area
)

@Serializable
data class Area(
    @SerialName("largeClasses")
    val largeAreas: List<LargeArea>
)

@Serializable
data class LargeArea(
    val largeClass: List<LargeClass>
)

@Serializable
data class LargeClass(
    val largeClassCode: String? = null,
    val largeClassName: String? = null,
    @SerialName("middleClasses")
    val middleAreas: List<MiddleArea>? = null,
)

@Serializable
data class MiddleArea(
    val middleClass: List<MiddleClass>
)

@Serializable
data class MiddleClass(
    val middleClassCode: String? = null,
    val middleClassName: String? = null,
    @SerialName("smallClasses")
    val smallAreas: List<SmallArea>? = null
)

@Serializable
data class SmallArea(
    val smallClass: List<SmallClass>
)

@Serializable
data class SmallClass(
    val smallClassCode: String? = null,
    val smallClassName: String? = null,
    @SerialName("detailClasses")
    val detailAreas: List<DetailArea>? = null
)

@Serializable
data class DetailArea(
    val detailClass: DetailClass
)

@Serializable
data class DetailClass(
    val detailClassCode: String,
    val detailClassName: String
)


