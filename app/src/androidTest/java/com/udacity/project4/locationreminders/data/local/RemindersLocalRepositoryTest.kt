package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Medium Test to test the repository
@MediumTest
class RemindersLocalRepositoryTest {

//    TODO: Add testing implementation to the RemindersLocalRepository.kt
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var remindersLocalRepository: RemindersLocalRepository
    private lateinit var database: RemindersDatabase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        remindersLocalRepository =
            RemindersLocalRepository(
                database.reminderDao(),
                Dispatchers.Main
            )

    }
    @After
    fun cleanUp() {
        database.close()
    }
    @Test
    fun getRemindersTest() = runBlocking {
        val reminder = ReminderDTO("alex","restaurant","KFC",1.2,3.2)
        remindersLocalRepository.saveReminder(reminder)
        val remindersList = remindersLocalRepository.getReminders() as? Result.Success
        assertThat(remindersList is Result.Success, `is`(true))
        assertThat(remindersList?.data, not(emptyList()))
    }
    @Test
    fun successfulGetReminder() = runBlocking {
        val reminder = ReminderDTO("alex","restaurant","KFC",1.2,3.2)
        remindersLocalRepository.saveReminder(reminder)
        val remindersList = remindersLocalRepository.getReminder(reminder.id) as? Result.Success
        assertThat(remindersList is Result.Success, `is`(true))
        remindersList as Result.Success
        assertThat(remindersList.data.title, `is`(reminder.title))
        assertThat(remindersList.data.description, `is`(reminder.description))
        assertThat(remindersList.data.latitude, `is`(reminder.latitude))
        assertThat(remindersList.data.longitude, `is`(reminder.longitude))
        assertThat(remindersList.data.location, `is`(reminder.location))
    }
    @Test
    fun errorGetReminder() = runBlocking {
        val reminder = ReminderDTO("alex","restaurant","KFC",1.2,3.2)
        remindersLocalRepository.saveReminder(reminder)
        remindersLocalRepository.deleteAllReminders()
        val remindersList = remindersLocalRepository.getReminder(reminder.id)
        remindersList as Result.Error
        assertThat(remindersList.message, `is`("Reminder not found!"))
    }
    @Test
    fun deleteRemindersTest()= runBlockingTest {

    }


}