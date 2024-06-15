package com.library.librarysystem.mysql.book;

import com.library.librarysystem.mysql.borrowingRecord.BorrowingRecord;
import com.library.librarysystem.mysql.borrowingRecord.BorrowingRecordRepository;
import com.library.librarysystem.mysql.borrowingRecord.BorrowingRecordService;
import com.library.librarysystem.util.exceptions.GeneralException;
import com.library.librarysystem.util.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
  private final BookRepository bookRepository;
  private final BorrowingRecordRepository borrowingRecordRepository;

  
    public List<Book> findAll(Book book){
        if(book == null || book.getStart() == null || book.getLimit() == null || (book.getStart() == 0 && book.getLimit() == 0)){
            return bookRepository.findAll();
        }
        return bookRepository.findAll(PageRequest.of(book.getStart()/book.getLimit(),book.getLimit())).toList();
    }

    public List<Book> findAll(){
        return findAll(null);
    }

    public Long getRecordsCount(){
        return bookRepository.count();
    }

    public Book getById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow( () -> new ResourceNotFoundException("Book","bookId",bookId.toString()));
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Long bookId, Book bookReq) {
        Book book = getById(bookId);
        if(book == null){
            throw new GeneralException("Book is Not Found");
        }
        return bookRepository.save(bookReq);
    }
    public void deleteById(Long bookId) {
        Book book = getById(bookId);
        if(book.getIsBorrowed()){
            throw new GeneralException("Book is not returned");
        }
        List<BorrowingRecord> borrowingRecordList = borrowingRecordRepository.findByBookBookIdAndReturnDateIsNotNull(bookId);
        if(!borrowingRecordList.isEmpty()){
            borrowingRecordRepository.deleteAll(borrowingRecordList);
        }
        bookRepository.deleteById(bookId);
    }
}
