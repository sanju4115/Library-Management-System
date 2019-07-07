package com.example.demo.service;


import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.entity.UserBooks;
import com.example.demo.exception.CustomExceptions;
import com.example.demo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class LibraryService {

    private static final int MAX_NUMBER_OF_BOOK = 2;
    private static final int MAX_NUMBER_OF_DAYS = 14;
    private static final int MAX_NUMBER_OF_MONTHS_FOR_BLACKLIST = 1;

    private final BookService bookService;
    private final UserService userService;
    private final UserBooksService userBooksService;
    private static Lock lock = new ReentrantLock();

    @Autowired
    public LibraryService(BookService bookService, UserService userService, UserBooksService userBooksService) {
        this.bookService = bookService;
        this.userService = userService;
        this.userBooksService = userBooksService;
    }

    public UserBooks lend(UserBooks userBooks){
        if (userBooks == null || userBooks.getBookId() == null || userBooks.getUserId() == null ){
            throw new CustomExceptions.InvalidRequestException("Lending request not valid");
        }
        Book book = bookService.findById(userBooks.getBookId());
        User user = userService.findById(userBooks.getUserId());

        lock.lock();
        UserBooks byBookId = userBooksService.findByBookId(book.getId());
        if (byBookId != null){
            throw new CustomExceptions.InvalidRequestException("Book with id "+ book.getId() + "already lent.");
        }

        List<UserBooks> byUserId = userBooksService.findAllByUserId(user.getId());
        if (byUserId != null && byUserId.size() == MAX_NUMBER_OF_BOOK){
            throw new CustomExceptions.InvalidRequestException(
                    "User already taken two books ");
        }
        userBooks.setCreatedAt(new Date());
        lock.unlock();
        return userBooksService.create(userBooks);
    }

    public String returnBook(UUID userBooksId){
        int penality = 0;
        String response = "Book returned successfully";
        if (userBooksId == null ){
            throw new CustomExceptions.InvalidRequestException("Return request not valid");
        }

        UserBooks byId = userBooksService.findById(userBooksId);
        if (byId == null){
            throw new CustomExceptions.InvalidRequestException("Return request not valid");
        }

        Period period = DateUtil.getPeriod(byId.getCreatedAt());
        int diffMonth = period.getMonths();
        int diffDays = period.getDays();
        if (diffMonth >= MAX_NUMBER_OF_MONTHS_FOR_BLACKLIST){
            User user = userService.findById(byId.getUserId());
            user.setBlackListed(true);
            userService.create(user);
            response =  "You have been blacklisted as return date exceeded 30 days";
        }else if (diffDays > MAX_NUMBER_OF_DAYS){
            int exceededDays = diffDays - MAX_NUMBER_OF_DAYS;
            if (exceededDays <=3){
                penality = exceededDays;
            }else if(exceededDays <= 6){
                penality = 3 + 2*(exceededDays-3);
            }else {
                penality = 9 + 3*(exceededDays-6);
            }
            response = "Book has been received with penality of " + penality;
        }
        userBooksService.deleteById(byId.getId());
        return response;
    }

    public List<UserBooks> getAllUserBooks() {
        return userBooksService.getAll();
    }
}
