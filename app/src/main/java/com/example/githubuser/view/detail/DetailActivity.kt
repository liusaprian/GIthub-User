package com.example.githubuser.view.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.githubuser.*
import com.example.githubuser.db.DatabaseContract.FavUserColumns.Companion.AVATAR
import com.example.githubuser.db.DatabaseContract.FavUserColumns.Companion.USERNAME
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.entity.User
import com.example.githubuser.viewmodel.DetailViewModel
import com.example.githubuser.viewmodel.FavoriteUserViewModel
import com.example.githubuser.viewmodel.FavoriteUserViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var user: User
    private var username: String? = null
    private var avatar: String? = null
    private var isFavorite: Boolean = false
    private var load = false

    companion object {
        private const val SAVED_USER_DETAIL = "saved_user_detail"
    }

    private val favUserViewModel: FavoriteUserViewModel by viewModels {
        FavoriteUserViewModelFactory((application as FavUserApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(USERNAME)
        avatar = intent.getStringExtra(AVATAR)

        loading(true)

        binding.username.text = username
        Glide.with(this)
            .load(avatar)
            .into(binding.avatar)

        if(savedInstanceState == null) {
            detailViewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(DetailViewModel::class.java)

            detailViewModel.setDetailUser(username!!)

            detailViewModel.getFollowers().observe(this, {
                if(it != null) user.followers = Converters.fromArrayList(it)
            })

            detailViewModel.getFollowing().observe(this, {
                if(it != null) user.following = Converters.fromArrayList(it)
            })

            detailViewModel.getDetailUser().observe(this, {
                if(it == null)
                    Toast.makeText(this, getString(R.string.load_failed), Toast.LENGTH_SHORT).show()
                else {
                    user = it
                    setUserDetail()
                }
                loading(false)
            })
        }
        else {
            user = savedInstanceState.getParcelable<User>(SAVED_USER_DETAIL) as User
            setUserDetail()
            loading(false)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, username!!)
        binding.viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(binding.viewPager)

        favUserViewModel.getFavoriteUser(username!!).observe(this) {
            if (it != null) setFavorite(true)
            else setFavorite(false)
        }

        supportActionBar?.title = username
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.favButton.setOnClickListener {
            if(load) {
                lifecycleScope.launch {
                    while((user.following == null || user.followers == null) && !isFavorite) delay(500)
                    val favoriteUser = User(
                        username!!,
                        avatar,
                        user.name,
                        user.company,
                        user.repository,
                        user.location,
                        user.followers,
                        user.following
                    )
                    withContext(Dispatchers.Main) {
                        if(!isFavorite) {
                            favUserViewModel.insert(favoriteUser)
                            setFavorite(true)
                        }
                        else {
                            favUserViewModel.delete(username!!)
                            setFavorite(false)
                        }
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(SAVED_USER_DETAIL, user)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUserDetail() {
        binding.name.text = user.name
        binding.company.text = user.company
        binding.repository.text = user.repository
        binding.location.text = user.location
    }

    private fun loading(state: Boolean) {
        if(state) {
            binding.detailProgressBar.visibility = View.VISIBLE
            load = false
        }
        else {
            binding.detailProgressBar.visibility = View.GONE
            load = true
        }
    }

    private fun setFavorite(favorite: Boolean) {
        isFavorite = favorite
        binding.favButton.setImageResource(
            if(favorite) R.drawable.ic_baseline_favorite_24
            else R.drawable.ic_baseline_favorite_border_24
        )
    }
}