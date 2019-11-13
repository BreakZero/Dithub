package com.di.dithub.comm.helpers

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class LoadMoreRecyclerViewScrollListener : EndlessRecyclerViewScrollListener {
    var hasMoreToLoad = true

    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
        if (hasMoreToLoad) fetchMore()
    }

    constructor(layoutManager: LinearLayoutManager) : super(layoutManager)

    constructor(layoutManager: GridLayoutManager) : super(layoutManager)

    constructor(layoutManager: StaggeredGridLayoutManager) : super(layoutManager)

    abstract fun fetchMore()
}