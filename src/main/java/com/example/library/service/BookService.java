package com.example.library.service;

import com.example.library.dto.BookDTO;
import com.example.library.entity.Book;
import com.example.library.exception.BaseErrorException;
import com.example.library.exception.ErrorMessage;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<BookDTO> findAllBook(){
       return bookRepository.findAllByDeletedAtIsNull()
               .stream()
               .map(book -> {
                   return convertToDto(book);
               })
               .collect(Collectors.toList());
    }

    public BookDTO getBook(Long id){
        Book book = bookRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BaseErrorException(HttpStatus.NOT_FOUND,
                        ErrorMessage.BOOK_NOT_FOUND.getMessage()));

        return convertToDto(book);
    }

    public BookDTO create(BookDTO request){
        Book book = convertToEntity(request);

        return convertToDto(bookRepository.save(book));
    }

    public BookDTO update(Long id, BookDTO request){
        Book book = bookRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BaseErrorException(HttpStatus.NOT_FOUND,
                        ErrorMessage.BOOK_NOT_FOUND.getMessage()));

        Book updatedBook = book.setTitle(request.getTitle())
                .setQty(request.getQty());

        return convertToDto(bookRepository.save(updatedBook));
    }

    public BookDTO delete(Long id){
        Book book = bookRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BaseErrorException(HttpStatus.NOT_FOUND,
                        ErrorMessage.USER_NOT_FOUND.getMessage()));

        return convertToDto(bookRepository.save(book.setDeletedAt(Instant.now())));
    }


    /*
    *
    * Private method
    *
    * */

    private BookDTO convertToDto(Book book){
        return bookMapper.bookToBookDto(book);
    }

    private Book convertToEntity(BookDTO bookDTO){
        return bookMapper.bookDtoToBook(bookDTO);
    }
}
