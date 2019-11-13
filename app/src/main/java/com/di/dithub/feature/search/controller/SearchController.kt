package com.di.dithub.feature.search.controller

import com.airbnb.epoxy.Typed2EpoxyController
import com.di.dithub.comm.component.LoadingFooterHolder
import com.di.dithub.model.response.RepoInfo

class SearchController(private val itemClick: (item: RepoInfo) -> Unit) : Typed2EpoxyController<List<RepoInfo>, Boolean>() {
    override fun buildModels(data: List<RepoInfo>?, hasMore: Boolean?) {
        data?.forEach {
            RepoModel(it) {
                itemClick.invoke(it)
            }.id(it.id)
                .addTo(this)
        }

        LoadingFooterHolder().id("footer").addIf(hasMore == true, this)
    }
}