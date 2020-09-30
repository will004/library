package com.example.library.controller;

import com.example.library.dto.BookDTO;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping()
    public List<BookDTO> bookList(){
        return bookService.findAllBook();
    }

    @PostMapping()
    public BookDTO create(@RequestBody BookDTO request){
        return bookService.create(request);
    }

    @GetMapping("/{id}")
    public BookDTO get(@PathVariable Long id){
        return bookService.getBook(id);
    }

    @PutMapping("/{id}")
    public BookDTO update(@PathVariable Long id, @RequestBody BookDTO request){
        return bookService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public BookDTO delete(@PathVariable Long id){
        return bookService.delete(id);
    }

}
