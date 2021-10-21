package com.jodel.jodelchallenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import com.jodel.jodelchallenge.R

import com.jodel.jodelchallenge.model.User

import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults

class UserAdapter (data: RealmResults<User>, val onUserItemClickListener: UserItemClickListener) :
    RealmRecyclerViewAdapter<User,
            UserViewHolder>(data, true) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_user, parent, false), onUserItemClickListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(data?.get(position))
    }

}