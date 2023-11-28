package com.example.githubapp.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.data.response.DetailUserResponse
import com.example.githubapp.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityDetailUserBinding

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportActionBar?.hide()

        val githubUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailUserViewModel::class.java)

        val githubUsername = intent.getStringExtra(EXTRA_USER)
        githubUserViewModel.fetchUserDetails(githubUsername.toString())

        val bundle = Bundle()
        bundle.putString(EXTRA_USER, githubUsername)

        githubUserViewModel.userDetail.observe(this){user ->
            displayUserDetails(user)
        }

        githubUserViewModel.isLoading.observe(this) {
            showProgressBar(it)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) {
                tabs, position -> tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }

    private fun displayUserDetails(user: DetailUserResponse){
        viewBinding.tvName.text = user.name
        viewBinding.tvUsername.text = user.login
        viewBinding.tvFollowers.text = "${user.followers} Followers"
        viewBinding.tvFollowing.text = "${user.following} Following"
        Glide.with(this)
            .load(user.avatarUrl)
            .into(viewBinding.imgUser)
    }

    private fun showProgressBar(isLoading: Boolean) {
        viewBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
