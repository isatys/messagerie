package com.ynov.chat.service;

import com.ynov.chat.model.Tache;
import com.ynov.chat.repository.TaskRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Optional<Tache> getTask(final Long id) {
        return taskRepository.findById(id);
    }

    public Iterable<Tache> getTasksByTodoListId(final Long id) {
        return taskRepository.findByTodoListId(id);
    }

    public Iterable<Tache> getTasks() {
        return taskRepository.findAll();
    }

    public void deleteTask(final Long id) {
        taskRepository.deleteById(id);
    }

    public Tache saveTask(Tache task) {
        Tache savedTask = taskRepository.save(task);
        return savedTask;
    }

}