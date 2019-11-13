package com.di.dithub.feature


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
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
        navMain.menu.findItem(R.id.menu_repos).isChecked = true
        navMain.setNavigationItemSelectedListener {
            if (it.itemId != R.id.menu_settings) it.isChecked = true

            when (it.itemId) {
                R.id.menu_repos -> {
                    launcherViewModel.selectModule(HomeModule.REPOSITORIES)
                }
                R.id.menu_stars -> {
                    launcherViewModel.selectModule(HomeModule.STARS)
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
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.mainPage) {
                launcherViewModel.moduleFlag.value?.let {
                    navMain.menu.findItem(
                        when (it) {
                            HomeModule.REPOSITORIES -> R.id.menu_repos
                            else -> R.id.menu_stars
                        }
                    ).isChecked = true
                }
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu?.findItem(R.id.menu_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        launcherViewModel.queryResult(query)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                navController.popBackStack()
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_search) {
            if (navController.currentDestination?.id == R.id.mainPage) {
                navController.navigate(R.id.action_to_search)
            }
            item.expandActionView()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
