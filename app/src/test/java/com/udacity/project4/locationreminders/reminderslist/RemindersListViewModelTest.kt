package com.udacity.project4.locationreminders.reminderslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertThat
import com.udacity.project4.locationreminders.MainCoroutineRule
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.core.IsNot
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RemindersListViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private lateinit var remindersRepository: FakeDataSource
    private lateinit var viewModel: RemindersListViewModel
    @After
    fun tearDown() {
        stopKoin()
    }
    @Test
    fun checkOfReminderListNotEmpty(){
        val reminder1 = ReminderDTO("alex","restaurant","mac",2.1,3.1)
        val reminder2 = ReminderDTO("alex","restaurant","KFC",2.5,3.5)
        val reminderList = mutableListOf(reminder1,reminder2)
        remindersRepository = FakeDataSource(reminderList)
        viewModel = RemindersListViewModel(ApplicationProvider.getApplicationContext(), remindersRepository)
        viewModel.loadReminders()
        assertThat( viewModel.remindersList.getOrAwaitValue(), (IsNot.not(emptyList())))
    }
    @Test
    fun loadRemindersTest(){
        mainCoroutineRule.pauseDispatcher()
        remindersRepository = FakeDataSource(mutableListOf())
        viewModel = RemindersListViewModel(ApplicationProvider.getApplicationContext(), remindersRepository)
        viewModel.loadReminders()
        assertThat(viewModel.showLoading.getOrAwaitValue(), CoreMatchers.`is`(true))
    }

}