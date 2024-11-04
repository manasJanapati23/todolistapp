package com.manny.todolistapp

data class Task(
    val id: Int,
    var title: String,
    var description: String,
    var isCompleted: Boolean,
    var priority: String // New field for priority
)
