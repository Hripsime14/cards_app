@file:Suppress("UNCHECKED_CAST")

package com.example.imagecard.extention

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFS_NAME = "cache_storage"

fun <T> DataStore<Preferences>.get(
    key: Preferences.Key<T>,
    defaultValue: T
): Flow<T> {
    return this.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[key] ?: defaultValue
        }
}

suspend fun <T> DataStore<Preferences>.put(key: Preferences.Key<T>, value: T) {
    this.edit { preferences ->
        preferences[key] = value
    }
}

fun Fragment.hideKeyboardFragment() {
    val inputMethodManager =
        requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        requireActivity().window.decorView.rootView.windowToken, 0
    )
}