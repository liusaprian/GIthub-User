package com.liusaprian.githubconsumer.view.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.liusaprian.githubconsumer.adapter.FavUserAdapter
import com.liusaprian.githubconsumer.adapter.SectionsPagerAdapter
import com.liusaprian.githubconsumer.databinding.ActivityDetailBinding
import com.liusaprian.githubconsumer.entity.User
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var favUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favUser = intent.getParcelableExtra(FavUserAdapter.USER_DETAIL)!!

        binding.username.text = favUser.username
        Glide.with(this)
            .load(favUser.avatar)
            .into(binding.avatar)
        binding.name.text = favUser.name
        binding.company.text = favUser.company
        binding.repository.text = favUser.repository
        binding.location.text = favUser.location

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, favUser.followers!!, favUser.following!!)
        binding.viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(binding.viewPager)

        supportActionBar?.title = favUser.username
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}