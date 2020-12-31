package com.example.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.entity.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class DetailViewModel : ViewModel() {

    private lateinit var name: String
    private lateinit var company: String
    private lateinit var repository: String
    private lateinit var location: String

    private var mutableFollowersList = MutableLiveData<ArrayList<User>>()
    private var mutableFollowingList = MutableLiveData<ArrayList<User>>()

    private val detailUser = MutableLiveData<User>()

    fun getDetailUser(): LiveData<User> = detailUser

    fun setDetailUser(username: String) {
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

                name = json.getString("name")
                if(name == "null") name = "-"

                company = json.getString("company")
                if(company == "null") company = "-"

                repository = json.getString("public_repos")

                location = json.getString("location")
                if(location == "null") location = "-"

                setFollowers(username)
                setFollowing(username)

                detailUser.postValue(
                    User(username, null, name, company, repository, location, null, null)
                )
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                detailUser.postValue(null)
            }

        })
    }

    fun getFollowers(): LiveData<ArrayList<User>> = mutableFollowersList

    fun setFollowers(username: String) {
        val followersList = ArrayList<User>()

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

                    val userItem = User(jsonObject.getString("login"), jsonObject.getString("avatar_url"))

                    followersList.add(userItem)
                }

                mutableFollowersList.postValue(followersList)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                mutableFollowersList.postValue(null)
            }

        })
    }

    fun getFollowing(): LiveData<ArrayList<User>> = mutableFollowingList

    fun setFollowing(username: String) {
        val followingList = ArrayList<User>()

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/following"
        client.addHeader("Authorization", "")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val stringJson = String(responseBody)
                val jsonArray = JSONArray(stringJson)

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)

                    val userItem = User(jsonObject.getString("login"), jsonObject.getString("avatar_url"))

                    followingList.add(userItem)
                }

                mutableFollowingList.postValue(followingList)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                mutableFollowingList.postValue(null)
            }

        })
    }
}