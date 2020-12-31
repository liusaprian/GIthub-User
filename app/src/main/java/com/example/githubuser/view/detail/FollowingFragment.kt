package com.example.githubuser.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.entity.User
import com.example.githubuser.viewmodel.DetailViewModel

class FollowingFragment(private val username: String) : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var followingAdapter: UserAdapter
    private lateinit var followingViewModel: DetailViewModel
    private var following = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retainInstance = true

        showLoading(true)

        followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        followingViewModel.setFollowing(username)

        followingViewModel.getFollowing().observe(requireActivity(), {
            if(it == null) showLoading(false)
            else {
                following = it
                if(following.isEmpty()) emptyList(true)
                else emptyList(false)
            }
        })

        followingAdapter = UserAdapter()
        binding.followingList.layoutManager = LinearLayoutManager(view.context)
        binding.followingList.adapter = followingAdapter
    }

    private fun emptyList(state: Boolean) {
        if(state) binding.followingText.text = getString(R.string.no_following)
        else followingAdapter.setUser(following)
        showText(state)
        showList(!state)
        showLoading(false)
    }

    private fun showText(state: Boolean) {
        binding.followingText.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showList(state: Boolean) {
        binding.followingList.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showLoading(state: Boolean) {
        binding.followingProgressBar.visibility = if(state) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}