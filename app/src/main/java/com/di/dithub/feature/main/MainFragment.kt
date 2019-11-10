package com.di.dithub.feature.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.di.dithub.R
import com.di.dithub.base.BaseFragment
import com.di.dithub.extensions.gone
import com.di.dithub.extensions.visible
import com.di.dithub.feature.login.SignInStatus
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment() {
    override fun layout(): Int = R.layout.fragment_main

    private val mainViewModel by sharedViewModel(MainViewModel::class)
    private val repoViewModel by viewModel(RepoViewModel::class)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.apply {
            signInStatus.observe(this@MainFragment, Observer {
                when (it) {
                    SignInStatus.SUCCESS -> {
                        failureLayout.gone()
                        rvContent.visible()
                        repoViewModel.fetchRepos()
                    }
                    else -> {
                        failureLayout.visible()
                        rvContent.gone()
                    }
                }
            })
        }

        setupView()
    }

    private fun setupView() {
        failureLayout.setOnClickListener {
            findNavController().navigate(R.id.action_to_sign_in)
        }
    }
}