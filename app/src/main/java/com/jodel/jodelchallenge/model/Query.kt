package com.jodel.jodelchallenge.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Query( @PrimaryKey
                  var id: Int = 1,
                  var query: String? = null) : RealmObject() {


}