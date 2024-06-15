package com.library.librarysystem.mysql.borrowingRecord;

import com.library.librarysystem.mysql.book.Book;
import com.library.librarysystem.mysql.book.BookService;
import com.library.librarysystem.mysql.patron.Patron;
import com.library.librarysystem.mysql.patron.PatronService;
import com.library.librarysystem.util.exceptions.GeneralException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookService bookService;
    private final PatronService patronService;
    public BorrowingRecord borrowBook(Long bookId, Long patronId){

        Book book = bookService.getById(bookId);
        Patron patron = patronService.getById(patronId);
        if(book.getIsBorrowed()){
            throw new GeneralException("Book is Already borrowed, Choose another book");
        }
        book.setIsBorrowed(true);
        BorrowingRecord borrowingRecord =BorrowingRecord.builder()
                .borrowingDate(LocalDateTime.now())
                .book(book)
                .patron(patron)
                .build();
        borrowingRecord = borrowingRecordRepository.save(borrowingRecord);
        bookService.save(book);
        return borrowingRecord;
    }

    public BorrowingRecord returnBook(Long bookId, Long patronId){

        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookBookIdAndPatronPatronIdAndReturnDateIsNull(bookId,patronId);
        if(borrowingRecord == null){
            throw new GeneralException("Book is already returned");
        }
        Book book = borrowingRecord.getBook();
        book.setIsBorrowed(false);
        borrowingRecord.setReturnDate(LocalDateTime.now());
        borrowingRecord = borrowingRecordRepository.save(borrowingRecord);
        bookService.save(book);
        return borrowingRecord;
    }
}
