package com.manny.todolistapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var taskTitleInput: EditText
    private lateinit var taskDescriptionInput: EditText
    private lateinit var priorityInput: Spinner
    private lateinit var addTaskButton: Button
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var taskList: MutableList<Task> = mutableListOf() // Initialize task list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskTitleInput = findViewById(R.id.taskTitleInput)
        taskDescriptionInput = findViewById(R.id.taskDescriptionInput)
        priorityInput = findViewById(R.id.priorityInput)
        addTaskButton = findViewById(R.id.addTaskButton)
        taskRecyclerView = findViewById(R.id.recyclerView)

        // Set up the spinner with priority options
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.priority_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        priorityInput.adapter = adapter

        // Set up RecyclerView
        taskAdapter = TaskAdapter(taskList) { task ->
            // Handle task completion
            task.isCompleted = true
            Toast.makeText(this, "${task.title} marked as completed", Toast.LENGTH_SHORT).show()
            taskAdapter.notifyDataSetChanged() // Notify adapter of data change
        }
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskRecyclerView.adapter = taskAdapter

        addTaskButton.setOnClickListener {
            val title = taskTitleInput.text.toString()
            val description = taskDescriptionInput.text.toString()
            val priority = priorityInput.selectedItem.toString() // Get selected priority

            // Check for empty fields
            if (title.isNotEmpty() && description.isNotEmpty()) {
                // Create a new task and add it to the list
                val task = Task(0, title, description, false, priority)
                taskList.add(task)
                taskAdapter.notifyItemInserted(taskList.size - 1) // Notify adapter of new task

                // Clear input fields
                taskTitleInput.text.clear()
                taskDescriptionInput.text.clear()
                priorityInput.setSelection(0) // Reset spinner to first item

            } else {
                // Show an error message
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
