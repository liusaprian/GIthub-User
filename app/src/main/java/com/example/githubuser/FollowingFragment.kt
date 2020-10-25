package com.example.githubuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_following.*
import org.json.JSONArray

class FollowingFragment(private val username: String) : Fragment() {

    private lateinit var followingAdapter: UserAdapter
    private var listFollowing = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)

        getFollowing(username)

        followingAdapter = UserAdapter()
        following_list.layoutManager = LinearLayoutManager(view.context)
        following_list.adapter = followingAdapter
    }

    private fun getFollowing(username: String) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/following"
        client.addHeader("Authorization", "token 77003919eca2d2b24b3ff9036a5fa20eaabc1ca8 ")
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

                    listFollowing.add(userItem)
                }
                if(listFollowing.isEmpty()) emptyList(true)
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
        following_progress_bar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showText(state: Boolean) {
        following_text.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showList(state: Boolean) {
        following_list.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun emptyList(state: Boolean) {
        if(state) {
            following_text.text = getString(R.string.no_following)
            showText(true)
            showList(false)
            showLoading(false)
        }
        else {
            followingAdapter.setUser(listFollowing)
            showList(true)
            showText(false)
            showLoading(false)
        }
    }

}