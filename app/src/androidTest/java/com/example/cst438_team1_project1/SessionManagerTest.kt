package com.example.cst438_team1_project1

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cst438_team1_project1.data.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

/**
 * androidTest
 *
 */

@RunWith(AndroidJUnit4::class)
class SessionManagerTest {

    @Test
    fun userLoggedIN() = runBlocking {

        val context = ApplicationProvider.getApplicationContext<Context>()
        val sessionManager = SessionManager(context)

        sessionManager.saveUserId(1)

        val loggedInUserId = sessionManager.readUserId.first()

        assertEquals(1, loggedInUserId)
    }

    @Test
    fun userLoggedOUT() = runBlocking {

        val context = ApplicationProvider.getApplicationContext<Context>()
        val sessionManager = SessionManager(context)

        sessionManager.saveUserId(1)

        sessionManager.removeUserId()

        val loggedInUserId = sessionManager.readUserId.first()

        assertNull(loggedInUserId)
    }
}