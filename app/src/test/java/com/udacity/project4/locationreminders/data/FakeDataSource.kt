package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource(var remindersList: MutableList<ReminderDTO>? = mutableListOf()) : ReminderDataSource {

//    Done: Create a fake data source to act as a double to the real data source
    private var error = false
    fun setReturnError(value: Boolean) {
        error = value
    }

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        if (error) {
            return Result.Error(
                "error to get Reminders"
            )
        }
        remindersList?.let { return Result.Success(it) }
        return Result.Error("error to get Reminders")
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        remindersList?.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        remindersList?.firstOrNull { it.id == id }?.let { return Result.Success(it) }
        return Result.Error("Reminder not found")
    }

    override suspend fun deleteAllReminders() {
        remindersList?.clear()
    }


}