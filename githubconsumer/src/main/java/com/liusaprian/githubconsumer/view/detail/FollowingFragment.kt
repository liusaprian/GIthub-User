package com.liusaprian.githubconsumer.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.liusaprian.githubconsumer.Converters
import com.liusaprian.githubconsumer.R
import com.liusaprian.githubconsumer.adapter.UserAdapter
import com.liusaprian.githubconsumer.databinding.FragmentFollowingBinding
import com.liusaprian.githubconsumer.entity.User

class FollowingFragment(private val following: String) : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var followingAdapter: UserAdapter
    private lateinit var followingList: ArrayList<User>

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

        followingAdapter = UserAdapter()
        binding.followingList.layoutManager = LinearLayoutManager(view.context)
        binding.followingList.adapter = followingAdapter

        followingList = Converters.fromString(following)
        if (followingList.isEmpty()) emptyList(true)
        else emptyList(false)
    }

    private fun showText(state: Boolean) {
        binding.followingText.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showList(state: Boolean) {
        binding.followingList.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun emptyList(state: Boolean) {
        if (state) binding.followingText.text = getString(R.string.no_following)
        else followingAdapter.setUser(followingList)
        showText(state)
        showList(!state)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}