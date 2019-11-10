package com.di.dithub.feature.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.di.dithub.R
import com.di.dithub.base.BaseFragment
import com.di.dithub.extensions.showSnackbar
import com.di.dithub.extensions.trimText
import com.di.dithub.feature.LauncherViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {
    override fun layout(): Int = R.layout.fragment_login

    private val viewModel by viewModel(LoginViewModel::class)
    private val launcherViewModel by sharedViewModel(LauncherViewModel::class)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            signInStatus.observe(this@LoginFragment, Observer {
                when (it) {
                    SignInStatus.SUCCESS -> {
                        launcherViewModel.signInStatus.update(it)
                        findNavController().popBackStack()
                    }
                    SignInStatus.FAILURE -> {
                        rootView.showSnackbar("Sign In Failure")
                    }
                    else -> {
                        // ignore
                    }
                }
            })

            viewModel.userInfo.observe(this@LoginFragment, Observer {
                launcherViewModel.userInfo.update(it)
            })
        }

        setupView()
    }

    private fun setupView() {

        btnSignIn.setOnClickListener {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive) {
                imm.hideSoftInputFromWindow(rootView.windowToken, 0)
            }
            val username = edtUsername.trimText()
            val password = edtPassword.trimText()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signIn(username, password)
            }
        }
    }
}