package com.library.librarysystem.mysql.borrowingRecord;

import com.library.librarysystem.mysql.book.Book;
import com.library.librarysystem.util.datawrapper.DataWrapper;
import com.library.librarysystem.util.datawrapper.DataWrapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService;
    private final DataWrapper<List<BorrowingRecord>> dwList;
    private final DataWrapper<BorrowingRecord> dw;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    ResponseEntity<DataWrapper<BorrowingRecord>> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId){
        BorrowingRecord borrowingRecord = borrowingRecordService.borrowBook(bookId,patronId);
        return ResponseEntity.ok(DataWrapperUtils.getWrapper(borrowingRecord,dw));
    }
    @PutMapping("/return/{bookId}/patron/{patronId}")
    ResponseEntity<DataWrapper<BorrowingRecord>> returnBook(@PathVariable Long bookId, @PathVariable Long patronId){
        BorrowingRecord borrowingRecord = borrowingRecordService.returnBook(bookId,patronId);
        return ResponseEntity.ok(DataWrapperUtils.getWrapper(borrowingRecord,dw));
    }


}
