package com.di.dithub.feature.main.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.di.dithub.model.response.RepoInfo

class RepoController : TypedEpoxyController<List<RepoInfo>>() {
    override fun buildModels(data: List<RepoInfo>?) {
        data?.forEach {
            RepoModel(it)
                .id(it.id)
                .addTo(this)
        }
    }

}