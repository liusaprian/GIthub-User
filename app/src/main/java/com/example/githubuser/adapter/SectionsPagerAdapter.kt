package com.example.githubuser.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.githubuser.R
import com.example.githubuser.view.detail.FollowersFragment
import com.example.githubuser.view.detail.FollowingFragment

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, private val username: String)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StyleRes
    private val TAB_TITLES = intArrayOf(R.string.tab_followers, R.string.tab_following)

    override fun getCount() = TAB_TITLES.size

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowersFragment(username)
            1 -> fragment = FollowingFragment(username)
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int) = context.resources.getString(TAB_TITLES[position])
}