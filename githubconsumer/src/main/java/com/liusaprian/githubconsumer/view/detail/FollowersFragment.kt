package com.liusaprian.githubconsumer.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.liusaprian.githubconsumer.Converters
import com.liusaprian.githubconsumer.R
import com.liusaprian.githubconsumer.adapter.UserAdapter
import com.liusaprian.githubconsumer.databinding.FragmentFollowersBinding
import com.liusaprian.githubconsumer.entity.User

class FollowersFragment(private val followers: String) : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private lateinit var followersAdapter: UserAdapter
    private lateinit var followersList: ArrayList<User>

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

        followersAdapter = UserAdapter()
        binding.followersList.layoutManager = LinearLayoutManager(view.context)
        binding.followersList.adapter = followersAdapter

        followersList = Converters.fromString(followers)
        if (followersList.isEmpty()) emptyList(true)
        else emptyList(false)
    }

    private fun showText(state: Boolean) {
        binding.followersText.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showList(state: Boolean) {
        binding.followersList.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun emptyList(state: Boolean) {
        if(state) binding.followersText.text = getString(R.string.no_followers)
        else followersAdapter.setUser(followersList)
        showText(state)
        showList(!state)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}