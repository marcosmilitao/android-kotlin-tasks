package com.example.tasks.service

import com.google.gson.annotations.SerializedName

class HeaderModel {

    @SerializedName("token")
    var token: String = ""

    @SerializedName("personalKey")
    var personalKey: String = ""

    @SerializedName("name")
    var name: String = ""
}