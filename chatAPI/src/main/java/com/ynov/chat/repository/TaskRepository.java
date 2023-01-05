package com.ynov.chat.repository;


import com.ynov.chat.model.Tache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends CrudRepository<Tache, Long> {
    // rajout d'une méthode de recherche spécifique
    List<Tache> findByTodoListId(Long todoListId);
}