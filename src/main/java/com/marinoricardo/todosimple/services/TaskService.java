package com.marinoricardo.todosimple.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marinoricardo.todosimple.models.Task;
import com.marinoricardo.todosimple.models.User;
import com.marinoricardo.todosimple.repositories.TaskRepository;
// import com.marinoricardo.todosimple.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    // private UserRepository userRepository;
    private UserService userService;

    private Task findById(Long id){
        Optional<Task> task = this.taskRepository.findById(id);

        return task.orElseThrow(() -> new RuntimeException(
            "Task nao encontrada, id: " + id + " Tipo: " + Task.class.getName()
        ));
    }

    @Transactional
    public Task create(Task obj){
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);

        return obj;
    }

    @Transactional
    public Task update(Task obj){
        Task newobj = findById(obj.getId());
        newobj.setDescription(obj.getDescription());
        return this.taskRepository.save(newobj);
    }

    public void delete(Long id){
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(
                "Nao foi possivel excluir pois tem ha entidades relacionadas"
            );
        }
    }


}
