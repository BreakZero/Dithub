package com.di.dithub.feature.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.di.dithub.R
import com.di.dithub.base.BaseFragment
import com.di.dithub.comm.Constant
import com.di.dithub.extensions.gone
import com.di.dithub.extensions.visible
import com.di.dithub.feature.LauncherViewModel
import com.di.dithub.feature.login.SignInStatus
import com.di.dithub.feature.main.controller.RepoController
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment() {
    override fun layout(): Int = R.layout.fragment_main

    private val launcherViewModel by sharedViewModel(LauncherViewModel::class)
    private val repoViewModel by viewModel(RepoViewModel::class)

    private val controller: RepoController = RepoController()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyViewModel()
        setupView()

        requireContext().getSharedPreferences(
            Constant.AccountConst.SP_ACCOUNT,
            Context.MODE_PRIVATE
        ).getString(Constant.AccountConst.KEY_TOKEN, "").apply {
            if (!this.isNullOrEmpty()) {
                toggleContent(false)
                repoViewModel.fetchRepos()
            }
        }
    }

    private fun toggleContent(empty: Boolean) {
        if (empty) {
            failureLayout.visible()
            rvContent.gone()
        } else {
            failureLayout.gone()
            rvContent.visible()
        }
    }

    private fun applyViewModel() {
        launcherViewModel.apply {
            signInStatus.observe(this@MainFragment, Observer {
                when (it) {
                    SignInStatus.SUCCESS -> {
                        toggleContent(false)
                        repoViewModel.fetchRepos()
                    }
                    else -> {
                        toggleContent(true)
                    }
                }
            })
        }

        repoViewModel.apply {
            repoResult.observe(this@MainFragment, Observer {
                toggleContent(false)
                controller.setData(it)
            })
        }
    }

    private fun setupView() {
        rvContent.setController(controller)
    }
}