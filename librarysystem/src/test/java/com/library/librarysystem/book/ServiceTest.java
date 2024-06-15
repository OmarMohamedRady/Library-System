package com.library.librarysystem.book;

import com.library.librarysystem.mysql.book.Book;
import com.library.librarysystem.mysql.book.BookRepository;
import com.library.librarysystem.mysql.book.BookService;
import com.library.librarysystem.mysql.borrowingRecord.BorrowingRecordRepository;
import com.library.librarysystem.util.exceptions.GeneralException;
import com.library.librarysystem.util.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_withPagination() {
        Book book = new Book();
        book.setStart(0);
        book.setLimit(10);
        List<Book> books = new ArrayList<>();
        books.add(new Book());

        when(bookRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(books));

        List<Book> result = bookService.findAll(book);

        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void findAll_withoutPagination() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.findAll();

        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getRecordsCount() {
        when(bookRepository.count()).thenReturn(10L);

        Long count = bookService.getRecordsCount();

        assertEquals(10, count);
        verify(bookRepository, times(1)).count();
    }

    @Test
    void getById_bookExists() {
        Book book = new Book();
        book.setBookId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getById(1L);

        assertEquals(book, result);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getById_bookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getById(1L));
    }

    @Test
    void save() {
        Book book = new Book();
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.save(book);

        assertEquals(book, result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void update() {
        Book existingBook = new Book();
        existingBook.setBookId(1L);
        Book bookRequest = new Book();
        bookRequest.setBookId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(bookRequest)).thenReturn(bookRequest);

        Book result = bookService.update(1L, bookRequest);

        assertEquals(bookRequest, result);
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(bookRequest);
    }

    @Test
    void deleteById_bookIsBorrowed() {
        Book book = new Book();
        book.setBookId(1L);
        book.setIsBorrowed(true);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThrows(GeneralException.class, () -> bookService.deleteById(1L));
    }

    @Test
    void deleteById_success() {
        Book book = new Book();
        book.setBookId(1L);
        book.setIsBorrowed(false);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowingRecordRepository.findByBookBookIdAndReturnDateIsNotNull(1L)).thenReturn(new ArrayList<>());

        bookService.deleteById(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }
}
