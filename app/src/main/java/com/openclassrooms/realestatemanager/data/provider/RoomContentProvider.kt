package com.openclassrooms.realestatemanager.data.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.data.database.RealtyDatabase
import com.openclassrooms.realestatemanager.data.model.Realty
import kotlinx.coroutines.runBlocking

class RoomContentProvider: ContentProvider() {

    override fun onCreate() = true

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        context?.let { context ->
            contentValues?.let {
                var contentUris: Uri? = null
                runBlocking {
                    val realty = Realty.fromContentValues(it)
                    val id = RealtyDatabase.getDatabase(context).realtyDao().insert(realty)
                    if (id != 0L) {
                        context.contentResolver.notifyChange(uri, null)
                        contentUris = ContentUris.withAppendedId(uri, id)
                    }
                }
                return contentUris
            }
        }

        throw IllegalArgumentException("Failed to insert row into $uri")
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        context?.let { context ->
            val id = ContentUris.parseId(uri).toInt()
            val cursor = RealtyDatabase.getDatabase(context).realtyDao().getRealtyCursorById(id)
            cursor.setNotificationUri(context.contentResolver, uri)
            return cursor
        }

        throw IllegalArgumentException("Failed to query row for uri $uri")
    }

    override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<out String>?): Int {
        context?.let { context ->
            contentValues?.let {
                var count = 0
                runBlocking {
                    count = RealtyDatabase.getDatabase(context).realtyDao().update(Realty.fromContentValues(it))
                }
                context.contentResolver.notifyChange(uri, null)
                return count
            }
        }

        throw IllegalArgumentException("Failed to update row into $uri")
    }

    override fun delete(uri: Uri, s: String?, strings: Array<out String>?): Int {
        context?.let { context ->
            var count = 0
            runBlocking {
                count = RealtyDatabase.getDatabase(context).realtyDao().delete(ContentUris.parseId(uri).toInt())
            }
            context.contentResolver.notifyChange(uri, null)
            return count
        }

        throw IllegalArgumentException("Failed to delete row into $uri")
    }

    override fun getType(uri: Uri): String? =
        "vnd.android.cursor.item/$AUTHORITY.$TABLE_NAME"

    companion object {
        const val AUTHORITY = "com.openclassrooms.realestatemanager.data.provider"
        val TABLE_NAME = Realty::class.java.simpleName
        val URI_ITEM: Uri = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }
}