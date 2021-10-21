package com.jodel.jodelchallenge.model

import com.google.gson.annotations.SerializedName

data class UserResponse (

	@field:SerializedName("total_count")
	var totalCount: Int,

	@field:SerializedName("incomplete_results")
	var isIncomplete: Boolean,

	@field:SerializedName("items")
	var users: List<User>

)
