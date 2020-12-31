package com.liusaprian.githubconsumer.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.liusaprian.githubconsumer.R
import com.liusaprian.githubconsumer.view.detail.FollowersFragment
import com.liusaprian.githubconsumer.view.detail.FollowingFragment

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager,
                           private val followers: String,
                           private val following: String
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StyleRes
    private val TAB_TITLES = intArrayOf(R.string.tab_followers, R.string.tab_following)

    override fun getCount() = TAB_TITLES.size

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowersFragment(followers)
            1 -> fragment = FollowingFragment(following)
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int) = context.resources.getString(TAB_TITLES[position])
}