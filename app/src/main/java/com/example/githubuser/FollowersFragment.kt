package com.example.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_followers.*
import org.json.JSONArray

class FollowersFragment(private val username: String) : Fragment() {

    private lateinit var followersAdapter: UserAdapter
    private var listFollowers = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retainInstance = true

        showLoading(true)

        getFollowers(username)

        followersAdapter = UserAdapter()
        followers_list.layoutManager = LinearLayoutManager(view.context)
        followers_list.adapter = followersAdapter
    }

    private fun getFollowers(username: String) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/followers"
        client.addHeader("Authorization", "")
        client.addHeader("User-Agent", "request")

        client.get(url, object: AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val stringJson = String(responseBody)
                val jsonArray = JSONArray(stringJson)

                for(i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)

                    val userItem = User()
                    userItem.username = jsonObject.getString("login")
                    userItem.avatar = jsonObject.getString("avatar_url")

                    listFollowers.add(userItem)
                }
                if(listFollowers.isEmpty()) emptyList(true)
                else emptyList(false)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Toast.makeText(activity, getString(R.string.load_failed), Toast.LENGTH_SHORT).show()
                showLoading(false)
            }

        })
    }

    private fun showLoading(state: Boolean) {
        followers_progress_bar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showText(state: Boolean) {
        followers_text.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showList(state: Boolean) {
        followers_list.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun emptyList(state: Boolean) {
        if(state) {
            followers_text.text = getString(R.string.no_followers)
            showText(true)
            showList(false)
            showLoading(false)
        }
        else {
            followersAdapter.setUser(listFollowers)
            showList(true)
            showText(false)
            showLoading(false)
        }
    }

}