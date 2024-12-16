package com.example.milestone.services;

import com.example.milestone.models.TaskModel;
import com.example.milestone.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<TaskModel> getAllTasks() {
        return (List<TaskModel>) taskRepository.findAll();
    }

    public Optional<TaskModel> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public TaskModel createTask(TaskModel task) {
        return taskRepository.save(task);
    }

    public TaskModel updateTask(Long id, TaskModel task) {
        if (taskRepository.existsById(id)) {
            task.setId(id);
            return taskRepository.save(task);
        } else {
            return null;
        }
    }

    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
