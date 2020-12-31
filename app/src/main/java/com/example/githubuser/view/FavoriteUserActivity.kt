package com.example.githubuser.view

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.db.DatabaseContract.FavUserColumns.Companion.CONTENT_URI
import com.example.githubuser.db.DatabaseContract.FavUserColumns.Companion.getColumnNames
import com.example.githubuser.R
import com.example.githubuser.adapter.FavUserAdapter
import com.example.githubuser.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: FavUserAdapter
    private val LOADER_FAV_USER = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favUserList.layoutManager = LinearLayoutManager(binding.favUserList.context)
        adapter = FavUserAdapter()
        binding.favUserList.adapter = adapter

        LoaderManager.getInstance(this).initLoader(LOADER_FAV_USER, null, loaderCallbacks)

        binding.swipeRefresh.setOnRefreshListener {
            refresh()
            binding.swipeRefresh.isRefreshing = false
        }

        supportActionBar?.title = getString(R.string.favorite_users)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refresh() {
        finish()
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun showList(isFavUserExist: Boolean) {
        binding.favUserList.visibility = if(isFavUserExist) View.VISIBLE else View.INVISIBLE
        binding.noUserFav.visibility = if(isFavUserExist) View.INVISIBLE else View.VISIBLE
    }

    private val loaderCallbacks = object : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            return CursorLoader(
                applicationContext,
                CONTENT_URI,
                getColumnNames(),
                null,
                null,
                null
            )
        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            adapter.setCursor(data)
            if(adapter.itemCount == 0) showList(false)
            else showList(true)
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {
            adapter.setCursor(null)
        }

    }
}