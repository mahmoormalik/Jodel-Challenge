package com.jodel.jodelchallenge.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jodel.jodelchallenge.R
import com.jodel.jodelchallenge.adapter.base.BaseViewHolder
import com.jodel.jodelchallenge.model.User
import kotlinx.android.synthetic.main.view_holder_user.view.*

class UserViewHolder(
    private val view: View,
    private val onUserItemClickListener: UserItemClickListener
) : BaseViewHolder<User?>(view) {
    override fun bind(item: User?) {

        view.username.text = item?.login
        if (item?.isFav == true) {
            view.ic_fav.setImageResource(R.drawable.ic_star_rate)
        } else view.ic_fav.setImageResource(R.drawable.ic_star_outline)

        view.ic_fav.setOnClickListener {

           val id = item?.id
            if (item?.isFav == true) {
                onUserItemClickListener.markUserFav(id, false)
            } else
                onUserItemClickListener.markUserFav(id, true)
        }

        view.setOnClickListener {
            onUserItemClickListener.showUserDetail(item?.id)
        }

        loadImage(item?.avatarUrl.orEmpty())
    }

    private fun loadImage(url: String) {
        Glide.with(view)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_placeholder_person)
            .into(view.userImage)
    }

}

