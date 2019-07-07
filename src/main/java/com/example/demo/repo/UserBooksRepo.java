package com.example.demo.repo;

import com.example.demo.entity.UserBooks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserBooksRepo extends JpaRepository<UserBooks, UUID> {

    UserBooks findByBookId(UUID bookId);
    List<UserBooks> findAllByUserId(UUID userId);
}
