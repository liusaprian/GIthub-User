package com.example.githubuser.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.example.githubuser.Converters
import com.example.githubuser.R
import com.example.githubuser.db.FavoriteUserDao
import com.example.githubuser.db.FavoriteUserRoomDatabase
import com.example.githubuser.entity.User

internal class StackRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private val favUsers = ArrayList<User>()
    private lateinit var roomDao: FavoriteUserDao

    override fun onCreate() {
        context.let {
            roomDao = FavoriteUserRoomDatabase.getDatabase(it).favUserDao()
        }
    }

    override fun onDataSetChanged() {
        val cursor = roomDao.getAllFavoriteUser()
        favUsers.clear()
        favUsers.addAll(Converters.fromCursorToList(cursor))
    }

    override fun onDestroy() {}

    override fun getCount() = favUsers.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(
            R.id.avatar,
            FavUserWidget.getBitmapFromURL(favUsers[position].avatar)
        )
        val extras = bundleOf(
            FavUserWidget.USERNAME to favUsers[position].username
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.avatar, fillInIntent)
        return rv
    }

    override fun getLoadingView() = null

    override fun getViewTypeCount() = 1

    override fun getItemId(p0: Int) = 0L

    override fun hasStableIds() = false
}