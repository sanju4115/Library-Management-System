package com.example.demo.service;

import com.example.demo.entity.UserBooks;
import com.example.demo.exception.CustomExceptions;
import com.example.demo.repo.UserBooksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserBooksService {

    private UserBooksRepo userBooksRepo;

    @Autowired
    public UserBooksService(UserBooksRepo userBooksRepo) {
        this.userBooksRepo = userBooksRepo;
    }


    public UserBooks create(UserBooks userBooks){
        return userBooksRepo.save(userBooks);
    }

    public List<UserBooks> getAll(){
        return userBooksRepo.findAll();
    }

    public UserBooks findById(UUID id){
        Optional<UserBooks> userOptional = userBooksRepo.findById(id);
        userOptional.orElseThrow(() -> new CustomExceptions.UserNotFoundException("No user book found with id " + id));
        return userOptional.get();
    }

    public UserBooks findByBookId(UUID uuid){
        return userBooksRepo.findByBookId(uuid);
    }


    public List<UserBooks> findAllByUserId(UUID uuid){
        return userBooksRepo.findAllByUserId(uuid);
    }

    public void deleteById(UUID uuid){
        userBooksRepo.deleteById(uuid);
    }

}
