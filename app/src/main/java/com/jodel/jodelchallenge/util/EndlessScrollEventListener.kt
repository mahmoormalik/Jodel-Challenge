package com.jodel.jodelchallenge.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class EndlessScrollEventListener(private val mLinearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    abstract fun onLoadMore(pageNum: Int, recyclerView: RecyclerView?)

    private val visibleThreshold = 5

    private var currentPage = 1

    private var previousTotalItemCount = 0

    private var loading = true

    private val startingPageIndex = 1

    private var totalItemCount = 0

    private var lastVisibleItemPosition = 0
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        totalItemCount = mLinearLayoutManager.itemCount
        lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition()


        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                loading = true
            }
        }


        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }


        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, recyclerView)
            loading = true
        }
    }

    fun reset() {
        currentPage = startingPageIndex
        previousTotalItemCount = 0
        loading = true
    }

}