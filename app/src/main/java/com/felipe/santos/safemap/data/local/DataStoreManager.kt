package com.felipe.santos.safemap.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val IS_APPROVED = booleanPreferencesKey("is_approved")
    }

    val isApprovedFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_APPROVED] ?: false
        }

    suspend fun setApproved(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_APPROVED] = value
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }
}