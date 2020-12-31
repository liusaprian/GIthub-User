package com.example.githubuser.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.FragmentFollowersBinding
import com.example.githubuser.entity.User
import com.example.githubuser.viewmodel.DetailViewModel

class FollowersFragment(private val username: String) : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private lateinit var followersAdapter: UserAdapter
    private lateinit var followersViewModel: DetailViewModel
    private var followers = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retainInstance = true

        showLoading(true)

        followersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        followersViewModel.setFollowers(username)

        followersViewModel.getFollowers().observe(requireActivity(), {
            if(it == null) showLoading(false)
            else {
                followers = it
                if(followers.isEmpty()) emptyList(true)
                else emptyList(false)
            }
        })

        followersAdapter = UserAdapter()
        binding.followersList.layoutManager = LinearLayoutManager(view.context)
        binding.followersList.adapter = followersAdapter
    }

    private fun emptyList(state: Boolean) {
        if(state) binding.followersText.text = getString(R.string.no_followers)
        else followersAdapter.setUser(followers)
        showText(state)
        showList(!state)
        showLoading(false)
    }

    private fun showText(state: Boolean) {
        binding.followersText.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showList(state: Boolean) {
        binding.followersList.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showLoading(state: Boolean) {
        binding.followersProgressBar.visibility = if(state) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}