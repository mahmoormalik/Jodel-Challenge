package com.jodel.jodelchallenge.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

// Only doing POJO for fields which are required in demo app.
open class User (

    @PrimaryKey
    var id: Int? = null,

    var login: String? = null,

    @field:SerializedName("avatar_url")
    var avatarUrl: String? = null,

    @field:SerializedName("html_url")
    var htmlUrl: String? = null,

    var isFav: Boolean? = null
) : RealmObject() {}
