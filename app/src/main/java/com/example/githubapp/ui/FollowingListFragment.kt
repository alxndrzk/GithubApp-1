package com.example.githubapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.data.response.UserFollowingResponseItem
import com.example.githubapp.databinding.FragmentFollowingBinding

class FollowingListFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: FollowingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arguments
        val userName = data?.getString(DetailUserActivity.EXTRA_USER).toString()

        val followingListViewModel =  ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingListViewModel::class.java)

        binding = FragmentFollowingBinding.bind(view)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowing.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFollowing.addItemDecoration(itemDecoration)

        adapter = FollowingListAdapter()
        binding.rvFollowing.adapter = adapter

        followingListViewModel.listFollowing(userName)

        followingListViewModel.followingList.observe(viewLifecycleOwner) {
                listFollower -> setFollowing(listFollower)
        }
        followingListViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setFollowing(following: List<UserFollowingResponseItem>) {
        val adapter = FollowingListAdapter()
        adapter.submitList(following)
        binding.rvFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}