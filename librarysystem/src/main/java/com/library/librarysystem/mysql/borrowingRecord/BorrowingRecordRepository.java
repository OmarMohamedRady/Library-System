package com.library.librarysystem.mysql.borrowingRecord;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord,Long> {

    BorrowingRecord findByBookIdAndPatronIdAndReturnDateIsNull(Long bookId, Long patronId);
    List<BorrowingRecord> findByBookIdAndReturnDateIsNotNull(Long bookId);
    List<BorrowingRecord> findByPatronIdAndReturnDateIsNotNull(Long patronId);
    List<BorrowingRecord> findByPatronIdAndReturnDateIsNull(Long patronId);
}
