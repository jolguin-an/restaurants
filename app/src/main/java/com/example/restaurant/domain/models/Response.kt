package com.example.restaurant.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Response(

    @field:SerializedName("html_attributions")
    val htmlAttributions: List<Any>,

    @field:SerializedName("results")
    val results: List<ResultsItem>,

    @field:SerializedName("status")
    val status: String
)

data class Southwest(

    @field:SerializedName("lng")
    val lng: Any,

    @field:SerializedName("lat")
    val lat: Any
)

data class PhotosItem(

    @field:SerializedName("photo_reference")
    val photoReference: String,

    @field:SerializedName("width")
    val width: Int,

    @field:SerializedName("html_attributions")
    val htmlAttributions: List<String>,

    @field:SerializedName("height")
    val height: Int
)

@Entity(tableName = "table_places")
data class ResultsItem(


    @field:SerializedName("business_status")
    val businessStatus: String,

    @field:SerializedName("icon")
    val icon: String,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("icon_background_color")
    val iconBackgroundColor: String,



    @ColumnInfo(name = "placeDescription")
    @field:SerializedName("reference")
    val reference: String,

    @field:SerializedName("user_ratings_total")
    val userRatingsTotal: Int,

    @field:SerializedName("price_level")
    val priceLevel: Int,

    @field:SerializedName("scope")
    val scope: String,
    @ColumnInfo(name = "placeName")
    @field:SerializedName("name")
    val name: String,


    @field:SerializedName("icon_mask_base_uri")
    val iconMaskBaseUri: String,

    @field:SerializedName("vicinity")
    val vicinity: String,


    @PrimaryKey
    @ColumnInfo(name = "placeId")
    @field:SerializedName("place_id")
    val placeId: String
)

data class PlusCode(

    @field:SerializedName("compound_code")
    val compoundCode: String,

    @field:SerializedName("global_code")
    val globalCode: String
)

data class Geometry(

    @field:SerializedName("viewport")
    val viewport: Viewport,

    @field:SerializedName("location")
    val location: Location
)

data class Northeast(

    @field:SerializedName("lng")
    val lng: Any,

    @field:SerializedName("lat")
    val lat: Any
)

data class Viewport(

    @field:SerializedName("southwest")
    val southwest: Southwest,

    @field:SerializedName("northeast")
    val northeast: Northeast
)

data class Location(

    @field:SerializedName("lng")
    val lng: Any,

    @field:SerializedName("lat")
    val lat: Any
)

data class OpeningHours(

    @field:SerializedName("open_now")
    val openNow: Boolean
)
