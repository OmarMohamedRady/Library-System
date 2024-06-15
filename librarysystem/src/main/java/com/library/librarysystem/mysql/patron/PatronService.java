package com.library.librarysystem.mysql.patron;

import com.library.librarysystem.mysql.book.Book;
import com.library.librarysystem.mysql.book.BookRepository;
import com.library.librarysystem.mysql.borrowingRecord.BorrowingRecord;
import com.library.librarysystem.mysql.borrowingRecord.BorrowingRecordRepository;
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
public class PatronService {
    private final PatronRepository patronRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;

    List<Patron> findAll(Patron patron){
        if(patron == null || patron.getStart() == null || patron.getLimit() == null || (patron.getStart() == 0 && patron.getLimit() == 0)){
            return patronRepository.findAll();
        }
        return patronRepository.findAll(PageRequest.of(patron.getStart()/patron.getLimit(),patron .getLimit())).toList();
    }
    List<Patron> findAll(){
        return findAll(null);
    }

    Long getRecordsCount(){
        return patronRepository.count();
    }

    public Patron getById(Long patronId) {
        return patronRepository.findById(patronId).orElseThrow( () -> new ResourceNotFoundException("Patron","patronId",patronId.toString()));
    }

    public Patron save(Patron patron) {
        return patronRepository.save(patron);
    }

    public Patron update(Long patronId, Patron patronReq) {
        Patron patron = getById(patronId);
        if(patron == null){
            throw new GeneralException("Patron is Not Found");
        }
        return patronRepository.save(patronReq);
    }
    public void deleteById(Long patronId) {
        Patron patron = getById(patronId);

        List<BorrowingRecord> borrowingRecordList = borrowingRecordRepository.findByPatronPatronIdAndReturnDateIsNotNull(patronId);
        if(!borrowingRecordList.isEmpty()){
            borrowingRecordRepository.deleteAll(borrowingRecordList);
        }else {
           borrowingRecordList = borrowingRecordRepository.findByPatronPatronIdAndReturnDateIsNull(patronId);
           if(!borrowingRecordList.isEmpty()){
               throw new GeneralException("Patron is still borrowing a book");
           }
        }
        patronRepository.deleteById(patronId);
    }
}
