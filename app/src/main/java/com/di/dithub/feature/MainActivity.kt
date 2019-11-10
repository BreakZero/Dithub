package com.di.dithub.feature

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.di.dithub.R
import com.di.dithub.comm.Constant
import com.di.dithub.feature.login.SignInStatus
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.drawerLayout
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    private val launcherViewModel by viewModel(LauncherViewModel::class)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT

        launcherViewModel.apply {
            userInfo.observe(this@MainActivity, Observer {
                it?.let {
                    navMain.getHeaderView(0).findViewById<AppCompatTextView>(R.id.tvNickname)
                        .text = it.nickname
                    navMain.getHeaderView(0).findViewById<AppCompatTextView>(R.id.tvReposNum)
                        .text = "${it.repoNum} Repositories"
                } ?: kotlin.run {
                    // maybe sign out to do.
                }
            })
            signInStatus.observe(this@MainActivity, Observer {
                if (it == SignInStatus.SUCCESS) fetchCurrUser()
            })
            fetchCurrUser()
        }

        setupView()
    }

    private fun setupView() {
        setSupportActionBar(dToolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        navController = Navigation.findNavController(this, R.id.navHost)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navMain, navController)
        navMain.setNavigationItemSelectedListener {
            Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
            it.isChecked = true
            drawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
        navMain.getHeaderView(0).findViewById<AppCompatImageView>(R.id.ivAvatar)
            .setOnClickListener {
                drawerLayout.closeDrawer(GravityCompat.START)
                getSharedPreferences(Constant.AccountConst.SP_ACCOUNT, Context.MODE_PRIVATE)
                    .getString(Constant.AccountConst.KEY_TOKEN, "").run {
                        if (isNullOrEmpty()) {
                            navController.navigate(R.id.action_to_sign_in)
                        } else {
                            Toast.makeText(this@MainActivity, "TO USER PROFILE", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.navHost),
            drawerLayout
        )
    }
}
