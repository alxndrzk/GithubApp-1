package com.example.githubapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.data.response.UserFollowerResponseItem
import com.example.githubapp.databinding.FragmentFollowersBinding
class FollowersListFragment : Fragment() {

    private lateinit var viewBinding: FragmentFollowersBinding
    private lateinit var followerAdapter: FollowerListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argumentBundle = arguments
        val userName = argumentBundle?.getString(DetailUserActivity.EXTRA_USER).toString()

        val followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowersViewModel::class.java)

        viewBinding = FragmentFollowersBinding.bind(view)

        val linearLayoutManager = LinearLayoutManager(requireActivity())
        viewBinding.rvFollower.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(requireContext(), linearLayoutManager.orientation)
        viewBinding.rvFollower.addItemDecoration(dividerItemDecoration)

        followerAdapter = FollowerListAdapter()
        viewBinding.rvFollower.adapter = followerAdapter

        followersViewModel.listFollower(userName)

        followersViewModel.follower.observe(viewLifecycleOwner) {
            followerList -> setFollower(followerList)
        }

        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setFollower(follower: List<UserFollowerResponseItem>) {
        val adapter = FollowerListAdapter()
        adapter.submitList(follower)
        viewBinding.rvFollower.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        viewBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}