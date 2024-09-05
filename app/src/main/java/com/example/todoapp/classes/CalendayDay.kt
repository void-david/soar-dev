package com.example.todoapp.classes

import java.util.Calendar

data class CalendarDay(
    val day: Int,
    val month: Int,
    val year: Int,
    val isWeekend: Boolean = isWeekend(day, month, year),
    val isToday: Boolean = isToday(day, month, year)
) {
    companion object {
        fun isToday(day: Int, month: Int, year: Int): Boolean {
            val today = Calendar.getInstance()
            return today.get(Calendar.DAY_OF_MONTH) == day &&
                    (today.get(Calendar.MONTH) + 1) == month &&
                    today.get(Calendar.YEAR) == year
        }

        fun isWeekend(day: Int, month: Int, year: Int): Boolean {
            val calendar = Calendar.getInstance()
            calendar.set(year, month - 1, day)
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY
        }
    }
}
