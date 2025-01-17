package com.jinying.springbootassignment.controllers;

import com.jinying.springbootassignment.models.Task;
import com.jinying.springbootassignment.services.TaskService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;


    // Handles GET request to retrieve all tasks.
    @GetMapping("/all")
    public ResponseEntity<Object> getAllTasks() {
        try {
            // Retrieve all tasks from the task service
            List<Task> tasks = taskService.getAllTask();

            // Check if the list of tasks is empty
            if (tasks.isEmpty()) {
                // Return a ResponseEntity with a custom message indicating no tasks found
                return new ResponseEntity<>("No tasks found", HttpStatus.OK);
            }

            // Return a ResponseEntity with the list of tasks
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            //Returns a ResponseEntity with a custom error message and HTTP status code
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET request to retrieve all completed tasks
    @GetMapping("/completed")
    public ResponseEntity<Object> getAllCompletedTasks() {
        try {
            // Retrieve all completed tasks from the task service
            List<Task> completedTasks = taskService.findAllCompletedTask();
            // Check if the list of completed tasks is empty
            if (completedTasks.isEmpty()) {
                // Return a ResponseEntity with a custom message indicating no completed tasks found
                return new ResponseEntity<>("No completed tasks found", HttpStatus.NOT_FOUND);
            }
            // Return a ResponseEntity with the list of completed tasks
            return ResponseEntity.ok(completedTasks);
        } catch (Exception e) {
            // Return a ResponseEntity with a custom error message and HTTP status code if an error occurs
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Handles GET request to retrieve all incomplete tasks.
    @GetMapping("/incomplete")
    public ResponseEntity<Object> getAllIncompleteTasks() {
        try {
            // Retrieve all incomplete tasks from the task service
            List<Task> incompleteTasks = taskService.findAllInCompleteTask();
            // Check if the list of incomplete tasks is empty
            if (incompleteTasks.isEmpty()) {
                // Return a ResponseEntity with a custom message indicating no incomplete tasks found
                return new ResponseEntity<>("No completed tasks found", HttpStatus.NOT_FOUND);
            }
            // Return a ResponseEntity with the list of incomplete tasks
            return ResponseEntity.ok(incompleteTasks);
        } catch (Exception e) {
            // Return a ResponseEntity with a custom error message and HTTP status code if an error occurs
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // create a new task
    @PostMapping("/add")
    public ResponseEntity<Object> createTask(@RequestBody Task task) {
        try {
            // Create a new task using the task service
            Task newTask = taskService.createNewTask(task);
            // Return a ResponseEntity with the newly created task and a 201 CREATED status code
            return new ResponseEntity<>(newTask, HttpStatus.CREATED);
        } catch (Exception e) {
            // // If an error occurs, return an error message and a 400 BAD REQUEST status code
            return new ResponseEntity<>("Error creating task. Task must not be blank and Completed can only be true or false. Please try again. ", HttpStatus.BAD_REQUEST);
        }
    }

     // Updates a task with the given ID.
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try {
            // Set the ID of the task to the ID from the URL path
            task.setId(id);

            // Update the task using the task service
            Task updatedTask = taskService.updateTask(task);

            // If the task is not found, return a 404 error response
            if (updatedTask == null) {
                return new ResponseEntity<>("No task found", HttpStatus.NOT_FOUND);
            }

            // Return the updated task
            return ResponseEntity.ok(updatedTask);
        } catch (Exception e) {
            // If an error occurs, return a 500 error response with the error message
            return new ResponseEntity<>("Error updating task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Deletes a task by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
        try {
            // Attempt to find the task with the given ID
            Task task = taskService.findTaskById(id);

            // If the task is not found, return a 404 response
            if (task == null) {
                // Return a ResponseEntity with a custom message indicating no completed tasks found
                return new ResponseEntity<>("No task found", HttpStatus.NOT_FOUND);
            }

            // Delete the task using the task service
            taskService.deleteTask(task);
            //Returns a ResponseEntity with a success message and HTTP status code after deleting a task.
            return new ResponseEntity<>("Task no. " + id + " deleted.", HttpStatus.OK);

        } catch (Exception e) {
            //Returns a ResponseEntity with a custom error message and HTTP status code
            return new ResponseEntity<>("An error occurred while deleting the task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
