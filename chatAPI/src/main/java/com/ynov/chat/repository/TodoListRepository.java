package com.ynov.chat.repository;


import com.ynov.chat.model.TodoList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TodoListRepository extends CrudRepository<TodoList, Long> {

}