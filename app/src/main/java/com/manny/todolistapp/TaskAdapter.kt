package com.manny.todolistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private var taskList: List<Task>, private val onTaskCompleted: (Task) -> Unit) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val taskDescription: TextView = itemView.findViewById(R.id.taskDescription)
        val taskCompleted: CheckBox = itemView.findViewById(R.id.taskCompleted)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position] // Get the task at the current position
        holder.taskTitle.text = task.title
        holder.taskDescription.text = task.description
        holder.taskCompleted.isChecked = task.isCompleted

        // Set the listener for checkbox change
        holder.taskCompleted.setOnCheckedChangeListener { _, isChecked ->
            // Avoid triggering the callback if the task is already marked as completed
            if (isChecked && !task.isCompleted) {
                onTaskCompleted(task)
            }
        }
    }

    override fun getItemCount() = taskList.size

    // Function to update the task list and notify the adapter
    fun updateTasks(newTaskList: List<Task>) {
        taskList = newTaskList.sortedBy { it.priority } // Sort tasks by priority when updated
        notifyDataSetChanged()
    }
}
