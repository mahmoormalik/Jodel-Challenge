package com.jodel.jodelchallenge.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("visible")
fun setVisible(view: View, show: Boolean) {
    view.visibility = if(show) View.VISIBLE else View.GONE
}

@BindingAdapter("isRefreshing")
fun setRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean) {
    view.isRefreshing = isRefreshing
}