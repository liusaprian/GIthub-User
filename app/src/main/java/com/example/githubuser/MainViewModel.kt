package com.example.githubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {

    val listUser = MutableLiveData<ArrayList<User>>()

    fun getUser(): LiveData<ArrayList<User>> = listUser
    fun setUser(username: String) {
        val userList = ArrayList<User>()

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "")
        client.addHeader("User-Agent", "request")

        val url = "https://api.github.com/search/users?q=$username"
        client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val stringJson = String(responseBody)
                val json = JSONObject(stringJson)
                val users = json.getJSONArray("items")

                if (users.length() == 0) {
                    listUser.postValue(null)
                    return
                }

                for (i in 0 until users.length()) {
                    val user = users.getJSONObject(i)
                    val userItem = User()
                    userItem.username = user.getString("login")
                    userItem.avatar = user.getString("avatar_url")

                    userList.add(userItem)
                }
                listUser.postValue(userList)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                listUser.postValue(null)
            }
        })
    }
}