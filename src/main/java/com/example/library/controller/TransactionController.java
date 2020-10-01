package com.example.library.controller;

import com.example.library.dto.TransactionDTO;
import com.example.library.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/borrow")
    public String borrowBook(@RequestBody TransactionDTO request){
        transactionService.borrowBook(request);
        return "Borrow book";
    }

    @PostMapping("/return")
    public String returnBook(@RequestBody TransactionDTO request){
        transactionService.returnBook(request);
        return "Return book";
    }
}
