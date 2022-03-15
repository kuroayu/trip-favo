package com.kuro.trip_favo.data.api

data class RakutenAreaResult(
    val areaClasses: AreaClasses
)

data class AreaClasses(
    val largeClasses: List<LargeClas>
)

//国
data class LargeClas(
    val largeClassCode: String,
    val largeClassName: String,
    val middleClasses: List<MiddleClas>
)

//都道府県
data class MiddleClas(
    val middleClassCode: String,
    val middleClassName: String,
    val smallClasses: List<SmallClas>
)

//市
data class SmallClas(
    val detailClasses: List<DetailClass>,
    val smallClassCode: String,
    val smallClassName: String
)

//市の詳細　地区
data class DetailClass(
    val detailClassCode: String,
    val detailClassName: String
)