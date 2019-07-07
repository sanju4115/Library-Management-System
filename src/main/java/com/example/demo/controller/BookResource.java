package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookResource {

    private final static Logger LOGGER = LoggerFactory.getLogger(BookResource.class);

    private BookService bookService;

    @Autowired
    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseBody
    public Book create(@RequestBody Book book ){
        LOGGER.info("Book create call by title {}", book.getTitle());
        return bookService.create(book);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Book>> get(@RequestParam(required = false) String query){
        return new ResponseEntity<>(bookService.getAll(query), HttpStatus.OK);
    }

    @GetMapping(value = "/{bookId}")
    @ResponseBody
    public ResponseEntity<Book> get(@PathVariable UUID bookId){
        LOGGER.info("Book get call by id {}", bookId);
        return new ResponseEntity<>(bookService.findById(bookId), HttpStatus.OK);
    }


}
