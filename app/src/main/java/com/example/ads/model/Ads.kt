package com.example.ads.model

data class Ads(
    var description: String?="",
    var phone: String?="",
    var price: String?="",
    var address: String?="",
    var imgURL: String? = null,
    var type :String?="",
    var id :String?=""
)
