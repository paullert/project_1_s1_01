package com.example.cst438_team1_project1.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val loggedInUserIdKey = intPreferencesKey("loggedInUserId")

class SessionManager(context: Context) {
    /*
    RESPONSIBILITIES:
        saveUserId(id)
        readUserId()
        removeUserId() TODO THIS WILL BE FOR LOGOUT

    ONLY STORE:
        loggedInUserId
        NOT PASSWORD
    */

    private val appContext = context.applicationContext

    //save logged in Users Id
    suspend fun saveUserId(id: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[loggedInUserIdKey] = id
        }
    }

    //read the Id. null means no user is logged in
    val readUserId: Flow<Int?> = appContext.dataStore.data.map { preferences ->
        preferences[loggedInUserIdKey]
    }

    //TODO REMOVEUSERID FOR LOGOUT

}