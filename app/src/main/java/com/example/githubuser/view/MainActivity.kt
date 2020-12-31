package com.example.githubuser.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.viewmodel.MainViewModel
import com.example.githubuser.R
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.view.reminder.ReminderActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel
    private var savedQuery: String? = null

    companion object {
        private const val SAVED_QUERY = "saved_query"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setWelcomeText()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        if(savedInstanceState != null) {
            val previousQuery = savedInstanceState.getString(SAVED_QUERY)
            showList()
            if(previousQuery != null) mainViewModel.setUser(previousQuery)
        }

        mainViewModel.getUser().observe(this, {
            if (it != null) userAdapter.setUser(it)
            else Toast.makeText(this, getString(R.string.null_username), Toast.LENGTH_SHORT).show()
            showLoading(false)
        })

        setRecyclerList()
    }

    private fun setRecyclerList() {
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        binding.listUser.layoutManager = LinearLayoutManager(this)
        binding.listUser.adapter = userAdapter
    }

    private fun setWelcomeText() {
        val startingText = SpannableString(getString(R.string.welcome_text))
        startingText.setSpan(RelativeSizeSpan(2f), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.startingText.text = startingText
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_button).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                hideText()
                showLoading(true)
                showList()
                savedQuery = query
                mainViewModel.setUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_QUERY, savedQuery)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.change_lang -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.favorite_page -> startActivity(Intent(this, FavoriteUserActivity::class.java))
            R.id.set_reminder -> startActivity(Intent(this, ReminderActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun hideText() {
        binding.startingText.visibility = View.GONE
    }

    private fun showList() {
        binding.listUser.visibility = View.VISIBLE
    }
}