package com.example.githubuser

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    private var usernameForApi: String? = null
    private var image: String? = null

    companion object {
        private const val SAVED_USERNAME = "username"
        private const val SAVED_IMAGE = "image"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        usernameForApi = intent.getStringExtra("username")
        image = intent.getStringExtra("avatar")

        if(savedInstanceState != null) {
            usernameForApi = savedInstanceState.getString(SAVED_USERNAME)
            image = savedInstanceState.getString(SAVED_IMAGE)
        }

        showLoading(true)

        username.text = usernameForApi
        Glide.with(this)
            .load(image)
            .into(avatar)

        getDetail(usernameForApi!!)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, usernameForApi!!)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

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

    private fun getDetail(username: String) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username"
        client.addHeader("Authorization", "")
        client.addHeader("User-Agent", "request")

        client.get(url, object: AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val stringJson = String(responseBody)
                val json = JSONObject(stringJson)

                name.text = json.getString("name")
                if(name.text == "null") name.text = "-"

                company.text = json.getString("company")
                if(company.text == "null") company.text = "-"

                repository.text = json.getString("public_repos")

                location.text = json.getString("location")
                if(location.text == "null") location.text = "-"

                showLoading(false)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Toast.makeText(this@DetailActivity, getString(R.string.load_failed), Toast.LENGTH_SHORT).show()
                showLoading(false)
            }

        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_USERNAME, usernameForApi)
        outState.putString(SAVED_IMAGE, image)
    }

    private fun showLoading(state: Boolean) {
        detail_progress_bar.visibility =  if(state) View.VISIBLE else View.GONE
    }
}