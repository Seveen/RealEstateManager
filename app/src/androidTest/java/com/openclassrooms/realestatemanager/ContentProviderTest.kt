package com.openclassrooms.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.data.database.RealtyDatabase
import com.openclassrooms.realestatemanager.data.provider.RoomContentProvider
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContentProviderTest {

    private lateinit var contentResolver: ContentResolver

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        RealtyDatabase.createInMemoryDatabase(context)

        contentResolver = context.contentResolver
    }

    @Test
    fun getItemsWhenNoItemInserted() {
        val cursor = contentResolver.query(ContentUris.withAppendedId(RoomContentProvider.URI_ITEM, 1), null, null, null, null)
        assertThat(cursor, notNullValue())
        cursor?.let {
            assertThat(it.count, `is`(0))
            it.close()
        }
    }

    @Test
    fun insertAndGetItem() {
        val realtyUri = contentResolver.insert(RoomContentProvider.URI_ITEM, generateRealty())

        val cursor = contentResolver.query(ContentUris.withAppendedId(RoomContentProvider.URI_ITEM, 1), null, null, null, null)
        assertThat(cursor, notNullValue())
        cursor?.let {
            assertThat(it.count, `is`(1))
            assertThat(it.moveToFirst(), `is`(true))
            assertThat(it.getString(it.getColumnIndexOrThrow("type")), `is`("Flat"))
            assertThat(it.getDouble(it.getColumnIndexOrThrow("priceInDollars")), `is`(100000.0))
        }
    }

    private fun generateRealty(): ContentValues {
        val values = ContentValues()
        values.put("id", 1)
        values.put("type", "Flat")
        values.put("priceInDollars", 100000.0)

        return values
    }
}