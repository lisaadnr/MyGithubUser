package com.dicoding.mygithubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuser.R
import com.dicoding.mygithubuser.data.database.FavoriteUser
import com.dicoding.mygithubuser.data.response.GithubUser
import com.dicoding.mygithubuser.databinding.ActivityFavoriteBinding
import com.dicoding.mygithubuser.ui.UserAdapter
import com.dicoding.mygithubuser.ui.detail.DetailUserActivity
import okhttp3.internal.notify

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel=ViewModelProvider(this).get(FavoriteViewModel::class.java)

        userAdapter = UserAdapter(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {
                val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                intent.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                intent.putExtra(DetailUserActivity.EXTRA_URL, data.avatarUrl)
                startActivity(intent)
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = userAdapter
        }

        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                userAdapter.submitList(list)
            }
        }
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<GithubUser> {
        val listUsers = ArrayList<GithubUser>()
        for (user in users){
            val userMapped = GithubUser(
                user.login,
                user.avatar_url,
                user.id
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}