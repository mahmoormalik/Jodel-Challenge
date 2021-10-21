package com.jodel.jodelchallenge.adapter

interface UserItemClickListener {

    fun showUserDetail(id: Int?)
    fun markUserFav(id: Int?, isFave: Boolean)
}