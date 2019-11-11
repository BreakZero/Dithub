package com.di.dithub.feature


import android.annotation.SuppressLint
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
import com.bumptech.glide.Glide
import com.di.dithub.R
import kotlinx.android.synthetic.main.activity_main.*
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
                    Glide.with(this@MainActivity)
                        .load(it.avatarUrl)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(navMain.getHeaderView(0).findViewById<AppCompatImageView>(R.id.ivAvatar))
                } ?: kotlin.run {
                    // maybe sign out to do.
                }
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
            if (it.itemId != R.id.menu_settings) it.isChecked = true

            when(it.itemId) {
                R.id.menu_repos -> {
                    launcherViewModel.selectModule(HomeModule.REPOSITORIES)
                }
                R.id.menu_stars -> {
                    launcherViewModel.selectModule(HomeModule.STARTS)
                }
                R.id.menu_settings -> {

                }
            }
            drawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
        navMain.getHeaderView(0).findViewById<AppCompatImageView>(R.id.ivAvatar)
            .setOnClickListener {
                drawerLayout.closeDrawer(GravityCompat.START)
                launcherViewModel.userInfo.value?.run {
                    Toast.makeText(this@MainActivity, "TO USER PROFILE", Toast.LENGTH_SHORT).show()
                } ?: kotlin.run {
                    navController.navigate(R.id.action_to_sign_in)
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
