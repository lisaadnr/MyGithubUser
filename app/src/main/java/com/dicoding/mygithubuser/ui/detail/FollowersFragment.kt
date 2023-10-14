package com.dicoding.mygithubuser.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuser.R
import com.dicoding.mygithubuser.data.response.GithubUser
import com.dicoding.mygithubuser.databinding.FragmentFollowBinding
import com.dicoding.mygithubuser.ui.UserAdapter

class FollowersFragment: Fragment(R.layout.fragment_follow){

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {
            }
        })

        showLoading(true)
        viewModel = ViewModelProvider(this).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it)
                showLoading(false)
            }
        }

        binding.apply {
            rvUserFollowers.setHasFixedSize(true)
            rvUserFollowers.layoutManager = LinearLayoutManager(activity)
            rvUserFollowers.adapter = adapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}