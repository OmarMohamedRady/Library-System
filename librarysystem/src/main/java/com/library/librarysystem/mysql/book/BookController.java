package com.library.librarysystem.mysql.book;


import com.library.librarysystem.util.datawrapper.DataWrapper;
import com.library.librarysystem.util.datawrapper.DataWrapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final DataWrapper<List<Book>> dwList;
    private final DataWrapper<Book> dw;
    @GetMapping("/")
    ResponseEntity<DataWrapper<List<Book>>> all(@RequestParam int start , @RequestParam int limit){
        Book book = Book.builder()
                .start(start)
                .limit(limit)
                .build();
        List<Book>  bookList= bookService.findAll(book);
        Long totalCount = bookService.getRecordsCount();
        return ResponseEntity.ok(DataWrapperUtils.getWrapper(bookList,dwList,totalCount.intValue()));

    }
    @GetMapping("/{id}")
    public ResponseEntity<DataWrapper<Book>> getById(@PathVariable(value = "id") Long bookId) {
        return ResponseEntity.ok(DataWrapperUtils.getWrapper(bookService.getById(bookId),dw));
    }

    @PostMapping("/save")
    public ResponseEntity<DataWrapper<Book>> save(@RequestBody Book book) {
        if(book.getBookId() != null) return ResponseEntity.ok(DataWrapperUtils.getWrapperError("You can't insert record that has an id.",dw));
        Book save = bookService.save(book);
        return ResponseEntity.ok(DataWrapperUtils.getWrapper(save,dw));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataWrapper<Book>> update(@PathVariable(value = "id") Long bookId,@RequestBody Book book){
        if(bookId== null) return ResponseEntity.ok(DataWrapperUtils.getWrapperError("You can't update record that has no id.",dw));
        Book save = bookService.update(bookId,book);
        return ResponseEntity.ok(DataWrapperUtils.getWrapper(save,dw));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataWrapper<String>> delete(@PathVariable(value = "id") Long bookId){
        bookService.deleteById(bookId);
        DataWrapper<String> dataWrapper = new DataWrapper<>();
        dataWrapper.setMessage("Data Deleted Successfully");
        return new ResponseEntity<>(dataWrapper,HttpStatus.OK);
    }
}
