package com.example.githubapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.data.response.ItemsItem
import com.example.githubapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        val layoutManager = LinearLayoutManager(this)
        viewBinding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        viewBinding.rvUser.addItemDecoration(itemDecoration)

        usersAdapter = UsersAdapter()
        viewBinding.rvUser.adapter = usersAdapter

        mainViewModel.users.observe(this) {listUser ->
            setUsersData(listUser)
        }

        mainViewModel.isLoadingIndicator.observe(this){
            showLoadingIndicator(it)
        }

        with(viewBinding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    val userName = searchView.text.toString()
                    mainViewModel.findUsers(userName)
                    searchView.hide()
                    false
                }
        }
    }

    private fun setUsersData(user: List<ItemsItem>) {
        val usersAdapter = UsersAdapter()
        usersAdapter.submitList(user)
        viewBinding.rvUser.adapter = usersAdapter
    }

    private fun showLoadingIndicator(isLoading: Boolean) {
        viewBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}