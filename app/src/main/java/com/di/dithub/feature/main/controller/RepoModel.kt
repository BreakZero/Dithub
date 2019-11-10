package com.di.dithub.feature.main.controller

import androidx.appcompat.widget.AppCompatTextView
import com.di.dithub.R
import com.di.dithub.comm.helpers.KotlinModel
import com.di.dithub.model.response.RepoInfo

class RepoModel(private val item: RepoInfo): KotlinModel(
    R.layout.repo_rv_item
) {
    private val tvName by bind<AppCompatTextView>(R.id.tvName)
    private val tvDesc by bind<AppCompatTextView>(R.id.tvDescription)
    override fun bind() {
        tvName.text = item.fullName
        tvDesc.text = item.description
    }
}