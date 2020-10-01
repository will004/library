package com.example.library.service;

import com.example.library.dto.TransactionDTO;
import com.example.library.entity.Book;
import com.example.library.entity.TransactionDetail;
import com.example.library.entity.TransactionHeader;
import com.example.library.exception.BaseErrorException;
import com.example.library.exception.ErrorMessage;
import com.example.library.repository.BookRepository;
import com.example.library.repository.TransactionDetailRepository;
import com.example.library.repository.TransactionHeaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    private TransactionHeaderRepository transactionHeaderRepository;

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void borrowBook(TransactionDTO request) {
        // validate book
        // 1. validate already borrow
        if (alreadyBorrow(request)) {
            throw new BaseErrorException(HttpStatus.NOT_FOUND, ErrorMessage.ALREADY_BORROW_BOOK.getMessage());
        }
        // 2. validate book stock
        if (!isBookAvailable(request)) {
            throw new BaseErrorException(HttpStatus.NOT_FOUND, ErrorMessage.BOOK_OUT_OF_STOCK.getMessage());
        }

        updateBookQty(request, TransactionType.BORROW);

        // save to db
        TransactionHeader transactionHeader = new TransactionHeader()
                .setTransactionDate(Instant.now())
                .setDeadlineDate(convertStringToInstant(request.getDeadlineDate()))
                .setUserId(request.getUserId());
        transactionHeaderRepository.save(transactionHeader);

        List<TransactionDetail> transactionDetails = request.getBookIds().stream()
                .map(bookId -> new TransactionDetail()
                        .setBookId(bookId)
                        .setHeaderId(transactionHeader.getId()))
                .collect(Collectors.toList());

        transactionDetailRepository.saveAll(transactionDetails);
    }

    @Transactional
    public void returnBook(TransactionDTO request) {

        updateBookQty(request, TransactionType.RETURN);

        TransactionHeader transactionHeader = transactionHeaderRepository.findByUserIdAndReturnDateIsNull(request.getUserId())
                .orElseThrow(() -> new BaseErrorException(HttpStatus.BAD_REQUEST, ErrorMessage.TRANSACTION_NOT_FOUND.getMessage()));

        transactionHeaderRepository.save(transactionHeader.setReturnDate(convertStringToInstant(request.getReturnDate())));
    }

    private Instant convertStringToInstant(String date) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.parse(date), LocalTime.of(23, 59, 59));
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());

        return Instant.from(zonedDateTime);
    }

    private boolean alreadyBorrow(TransactionDTO request){
        Long userId = request.getUserId();

        if (transactionHeaderRepository.findAllByUserIdAndReturnDateIsNullAndDeletedAtIsNull(userId).isEmpty()) {
            return false;
        }

        return true;

//        List<Long> transactionHeaderIds = transactionHeaderRepository.findAllByUserIdAndReturnDateIsNullAndDeletedAtIsNull(userId)
//                .stream()
//                .map(TransactionHeader::getId)
//                .collect(Collectors.toList());
//        Set<Long> bookIds = transactionDetailRepository.findAllByHeaderIdInAndDeletedAtIsNull(transactionHeaderIds)
//                .stream()
//                .map(TransactionDetail::getBookId)
//                .collect(Collectors.toSet());

        // Collections.disjoint returns false when has intersection, so we flip the boolean
        // user already borrow the book(s) if the collections have intersection, so we have to return true
//        return !Collections.disjoint(bookIds, request.getBookIds());
    }

    private boolean isBookAvailable(TransactionDTO request) {
        return request.getBookIds()
                .stream()
                .allMatch(bookId -> bookRepository.findByIdAndDeletedAtIsNull(bookId)
                                .orElseThrow(() -> new BaseErrorException(HttpStatus.NOT_FOUND,
                                        ErrorMessage.BOOK_NOT_FOUND.getMessage()))
                .getQty() > 0);
    }

    private void updateBookQty(TransactionDTO request, TransactionType type) {
        int n;
        if (type.equals(TransactionType.BORROW)) n = -1;
        else n = 1;

        request.getBookIds().stream()
        .forEach(bookId -> {
            Book bookToUpdated = bookRepository.findByIdAndDeletedAtIsNull(bookId)
                    .orElseThrow(() -> new BaseErrorException(HttpStatus.BAD_REQUEST, ErrorMessage.BOOK_NOT_FOUND.getMessage()));
            bookToUpdated.setQty(bookToUpdated.getQty() + n);
            bookRepository.save(bookToUpdated);
        });
    }
}
