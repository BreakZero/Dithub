package com.di.dithub.feature.search.controller

import androidx.appcompat.widget.AppCompatTextView
import com.di.dithub.R
import com.di.dithub.comm.helpers.KotlinModel
import com.di.dithub.model.response.RepoInfo

class RepoModel(
    private val item: RepoInfo, private val itemClick: () -> Unit
) : KotlinModel(R.layout.repo_rv_item) {
    private val tvName by bind<AppCompatTextView>(R.id.tvName)
    private val tvDesc by bind<AppCompatTextView>(R.id.tvDescription)
    private val tvStarCount by bind<AppCompatTextView>(R.id.tvStarCount)
    private val tvLanguage by bind<AppCompatTextView>(R.id.tvLanguage)
    private val tvUpdateTime by bind<AppCompatTextView>(R.id.tvUpdateTime)

    override fun bind() {
        view?.setOnClickListener {
            itemClick.invoke()
        }
        tvName.text = item.fullName
        tvDesc.text = item.description
        tvStarCount.text = item.starCount.toString()
        tvLanguage.text = item.language
        tvUpdateTime.text = item.updateTime.toString()
    }
}