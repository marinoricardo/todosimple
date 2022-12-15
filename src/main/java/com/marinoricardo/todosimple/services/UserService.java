package com.marinoricardo.todosimple.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marinoricardo.todosimple.models.User;
// import com.marinoricardo.todosimple.repositories.TaskRepository;
import com.marinoricardo.todosimple.repositories.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    // @Autowired
    // private TaskRepository taskRepository;

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
            "Usuario nao encontrado! Id: " + id + ", Tipo: " + User.class.getName()
        ));
    }

    // usar sempre que fazer uma transacao no banco de dados
    @Transactional
    public User create(User obj){
        obj.setId(null);
        obj = this.userRepository.save(obj);
        // salvar as tasks do user
        // this.taskRepository.saveAll(obj.getTasks());
        return obj;
    }

    @Transactional
    public User update(User obj){
        User newobj = findById(obj.getId());
        newobj.setPassword(obj.getPassword());

        return this.userRepository.save(newobj);
    }

    public void delete(Long id){
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(
                "Nao eh possivel excluir pois ha tasks relacionadas."
            );
        }
    }


}
