package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.udacity.project4.locationreminders.data.dto.ReminderDTO

import org.junit.runner.RunWith;

import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class RemindersDaoTest {

//    TODO: Add testing implementation to the RemindersDao.kt
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: RemindersDatabase
    @Before
    fun initDb() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            RemindersDatabase::class.java
        ).build()
    }
    @After
    fun closeDb() = database.close()

    @Test
    fun getRemindersTest() = runBlockingTest {
        val reminder = ReminderDTO("alex","restaurant","KFC",1.2,3.2)
        database.reminderDao().saveReminder(reminder)
        val remindersList = database.reminderDao().getReminders()
        assertThat(remindersList.isEmpty(), `is`(false))
    }

    @Test
    fun getReminderByIdTest() = runBlockingTest {
        val reminder = ReminderDTO("alex","restaurant","KFC",1.2,3.2)
        database.reminderDao().saveReminder(reminder)
        val loaded = database.reminderDao().getReminderById(reminder.id)
        assertThat<ReminderDTO>(loaded as ReminderDTO, notNullValue())
        assertThat(loaded.id, `is`(reminder.id))
        assertThat(loaded.title, `is`(reminder.title))
        assertThat(loaded.description, `is`(reminder.description))
        assertThat(loaded.location, `is`(reminder.location))
        assertThat(loaded.latitude, `is`(reminder.latitude))
        assertThat(loaded.longitude, `is`(reminder.longitude))

    }
    @Test
    fun deleteRemindersTest()= runBlockingTest {
        val reminder = ReminderDTO("alex","restaurant","KFC",1.2,3.2)
        database.reminderDao().saveReminder(reminder)
    }


}