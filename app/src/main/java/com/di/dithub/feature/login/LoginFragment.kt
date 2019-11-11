package com.di.dithub.feature.login

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.di.dithub.R
import com.di.dithub.base.BaseFragment
import com.di.dithub.extensions.showSnackbar
import com.di.dithub.extensions.trimText
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {
    override fun layout(): Int = R.layout.fragment_login

    private val viewModel by viewModel(LoginViewModel::class)

    private var loadingDialog: DialogPlus? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            signInResult.observe(this@LoginFragment, Observer {
                if (loadingDialog?.isShowing == true) loadingDialog?.dismiss()
                it?.run {
                    when (this.code) {
                        0 -> {
                            findNavController().popBackStack()
                        }
                        1 -> {
                            rootView.showSnackbar("Sign In Failure")
                        }
                        else -> {
                            // ignore
                        }
                    }
                }
            })
        }

        setupView()
    }

    private fun setupView() {
        loadingDialog = DialogPlus.newDialog(requireContext()).apply {
            setContentHolder(ViewHolder(R.layout.dialog_loading))
            isCancelable = true
            setGravity(Gravity.CENTER)
            setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
            contentBackgroundResource = android.R.color.transparent
        }.create()

        btnSignIn.setOnClickListener {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive) {
                imm.hideSoftInputFromWindow(rootView.windowToken, 0)
            }
            val username = edtUsername.trimText()
            val password = edtPassword.trimText()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                loadingDialog?.show()
                viewModel.signIn(username, password)
            }
        }
    }
}