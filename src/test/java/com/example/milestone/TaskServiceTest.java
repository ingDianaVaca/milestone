package com.example.milestone;

import com.example.milestone.models.TaskModel;
import com.example.milestone.repositories.TaskRepository;
import com.example.milestone.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;

public class TaskServiceTest {

    // Since the layer service is to be tested, we use TaskService
    @InjectMocks
    private TaskService taskService;

    // Replace object to simulate behaviour
    @Mock
    private TaskRepository taskRepository;

    // Initializes a clean test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Create two tasks, to eventually return the list with the two tasks (since the test brings “all” tasks, in this case two)
    @Test
    void testGetAllTasks() {
        TaskModel task1 = new TaskModel();
        task1.setId(1L);
        task1.setTitle("Task1");
        task1.setDescription("Description1");
        task1.setStatus("Pending");

        TaskModel task2 = new TaskModel();
        task2.setId(2L);
        task2.setTitle("Task2");
        task2.setDescription("Description2");
        task2.setStatus("Completed");

        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<TaskModel> tasks = taskService.getAllTasks();

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    // Create a task and then search for it by its ID (in this case a task with ID = 1 was created) and check that the titles match
    @Test
    void testGetTaskById_TaskExists() {
        TaskModel task = new TaskModel();
        task.setId(1L);
        task.setTitle("Task1");
        task.setDescription("Description1");
        task.setStatus("Pending");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<TaskModel> retrievedTask = taskService.getTaskById(1L);

        assertTrue(retrievedTask.isPresent());
        assertEquals("Task1", retrievedTask.get().getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    // Skip the creation of a task to verify that the task with the id is not present (in this case it's looking for an ID = 1, but it does not exist)
    @Test
    void testGetTaskById_TaskDoesNotExist() {

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<TaskModel> retrievedTask = taskService.getTaskById(1L);

        assertFalse(retrievedTask.isPresent());
        verify(taskRepository, times(1)).findById(1L);
    }

    // Creates a predefined task to later check that the task is not null
    @Test
    void testCreateTask() {
        TaskModel task = new TaskModel();
        task.setTitle("New Task");
        task.setDescription("New Description");
        task.setStatus("Pending");

        when(taskRepository.save(task)).thenReturn(task);

        TaskModel createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        assertEquals("New Task", createdTask.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    // Verify that the task with ID = 1 exists, if it does, proceed to delete the task.
    // If it does not (task with ID = 1 does not exist), it does nothing.
    @Test
    void testDeleteTask_TaskExists() {

        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        boolean isDeleted = taskService.deleteTask(1L);

        assertTrue(isDeleted);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    // We verify that the ID to be tested does not exist (in this case ID = 1), therefore it does not exist in the DB
    // As it does not exist, it returns a statement that, indeed, the task does not exist
    @Test
    void testDeleteTask_TaskDoesNotExist() {

        when(taskRepository.existsById(1L)).thenReturn(false);

        boolean isDeleted = taskService.deleteTask(1L);

        assertFalse(isDeleted);
        verify(taskRepository, never()).deleteById(1L);
    }
}
