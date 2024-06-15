package com.library.librarysystem.mysql.borrowingRecord;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord,Long> {

    BorrowingRecord findByBookBookIdAndPatronPatronIdAndReturnDateIsNull(Long bookId, Long patronId);
    List<BorrowingRecord> findByBookBookIdAndReturnDateIsNotNull(Long bookId);
    List<BorrowingRecord> findByPatronPatronIdAndReturnDateIsNotNull(Long patronId);
    List<BorrowingRecord> findByPatronPatronIdAndReturnDateIsNull(Long patronId);
}
