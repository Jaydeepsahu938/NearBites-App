package com.example.nearbites.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class NearbyRestroResponseModel(
    val type: String,
    val features: List<Feature>,
)

data class Feature(
    val type: String,
    val properties: Properties,
    val geometry: Geometry,
)

data class Properties(
    val name: String,
    val country: String,
    @SerializedName("country_code")
    val countryCode: String,
    val state: String,
    val city: String?,
    val postcode: String,
    val suburb: String?,
    val street: String,
    val housenumber: String?,
    @SerializedName("iso3166_2")
    val iso31662: String,
    val lon: Double,
    val lat: Double,
    @SerializedName("state_code")
    val stateCode: String,
    val formatted: String,
    @SerializedName("address_line1")
    val addressLine1: String,
    @SerializedName("address_line2")
    val addressLine2: String,
    val categories: List<String>,
    val details: List<String>,
    val datasource: Datasource,
    @SerializedName("opening_hours")
    val openingHours: String?,
    val ele: Long?,
    val facilities: Facilities?,
    val catering: Catering?,
    @SerializedName("place_id")
    val placeId: String,
    val district: String?,
    val contact: Contact?,
    @SerializedName("city_block")
    val cityBlock: String?,
    @SerializedName("payment_options")
    val paymentOptions: PaymentOptions?,
    val county: String?,
    @SerializedName("state_district")
    val stateDistrict: String?,
    val brand: String?,
    val operator: String?,
    val restrictions: Restrictions?,
    val building: Building?,
)

data class Datasource(
    val sourcename: String,
    val attribution: String,
    val license: String,
    val url: String,
    val raw: Raw,
)

data class Raw(
    val bar: String?,
    val ele: Long?,
    val name: String,
    val stars: Double?,
    @SerializedName("osm_id")
    val osmId: Long,
    val amenity: String,
    val cuisine: String?,
    val smoking: String?,
    @SerializedName("osm_type")
    val osmType: String,
    @SerializedName("addr:city")
    val addrCity: String?,
    val wheelchair: String?,
    @SerializedName("addr:street")
    val addrStreet: String?,
    val reservation: String?,
    val microbrewery: String?,
    @SerializedName("addr:postcode")
    val addrPostcode: Long?,
    @SerializedName("opening_hours")
    val openingHours: String?,
    @SerializedName("diet:vegetarian")
    val dietVegetarian: String?,
    @SerializedName("addr:housenumber")
    val addrHousenumber: String?,
    @SerializedName("air_conditioning")
    val airConditioning: String?,
    @SerializedName("diet:non-vegetarian")
    val dietNonVegetarian: String?,
    val phone: Any?,
    @SerializedName("postal_code")
    val postalCode: Long?,
    val level: Long?,
    @SerializedName("contact:mobile")
    val contactMobile: String?,
    @SerializedName("outdoor_seating")
    val outdoorSeating: String?,
    val delivery: String?,
    val takeaway: String?,
    @SerializedName("addr:unit")
    val addrUnit: String?,
    @SerializedName("check_date")
    val checkDate: String?,
    @SerializedName("payment:upi")
    val paymentUpi: String?,
    @SerializedName("payment:cash")
    val paymentCash: String?,
    val mobile: String?,
    @SerializedName("diet:eggs")
    val dietEggs: String?,
    val brand: String?,
    val toilets: String?,
    @SerializedName("contact:phone")
    val contactPhone: String?,
    val access: String?,
    val building: String?,
    val operator: String?,
    @SerializedName("roof:shape")
    val roofShape: String?,
    @SerializedName("self_service")
    val selfService: String?,
    @SerializedName("building:levels")
    val buildingLevels: Long?,
    @SerializedName("building:material")
    val buildingMaterial: String?,
)

data class Facilities(
    val wheelchair: Boolean?,
    val toilets: Boolean?,
    val takeaway: Boolean?,
    val delivery: Boolean?,
    @SerializedName("outdoor_seating")
    val outdoorSeating: Boolean?,
    @SerializedName("air_conditioning")
    val airConditioning: Boolean?,
    val smoking: Boolean?,
)

data class Catering(
    val cuisine: String,
    val diet: Diet?,
    val stars: Double?,
    val reservation: String?,
)

data class Diet(
    val eggs: Boolean?,
    val vegetarian: Boolean,
    @SerializedName("non-vegetarian")
    val nonVegetarian: Boolean?,
)

data class Contact(
    val phone: Any?,
)

data class PaymentOptions(
    val upi: Boolean,
    val cash: Boolean,
)

data class Restrictions(
    val access: String,
)

data class Building(
    val levels: Long,
    val material: String,
    val roof: Roof,
)

data class Roof(
    val shape: String,
)

data class Geometry(
    val type: String,
    val coordinates: List<Double>,
)
