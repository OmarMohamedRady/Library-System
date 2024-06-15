package com.library.librarysystem.mysql.patron;

import com.library.librarysystem.mysql.book.Book;
import com.library.librarysystem.util.datawrapper.DataWrapper;
import com.library.librarysystem.util.datawrapper.DataWrapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
@RequiredArgsConstructor
public class PatronController {

    private final PatronService patronService;
    private final DataWrapper<List<Patron>> dwList;
    private final DataWrapper<Patron> dw;
    @GetMapping("/")
    ResponseEntity<DataWrapper<List<Patron>>> all(@RequestParam int start , @RequestParam int limit){
        Patron patron = Patron.builder()
                .start(start)
                .limit(limit)
                .build();
        List<Patron>  patronList= patronService.findAll(patron);
        Long totalCount = patronService.getRecordsCount();
        return ResponseEntity.ok(DataWrapperUtils.getWrapper(patronList,dwList,totalCount.intValue()));

    }
    @GetMapping("/{id}")
    public ResponseEntity<DataWrapper<Patron>> getById(@PathVariable(value = "id") Long patronId) {
        return ResponseEntity.ok(DataWrapperUtils.getWrapper(patronService.getById(patronId),dw));
    }

    @PostMapping("/")
    public ResponseEntity<DataWrapper<Patron>> save(@RequestBody Patron patron) {
        if(patron.getPatronId() != null) return ResponseEntity.ok(DataWrapperUtils.getWrapperError("You can't insert record that has an id.",dw));
        Patron save = patronService.save(patron);
        return ResponseEntity.ok(DataWrapperUtils.getWrapper(save,dw));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataWrapper<Patron>> update(@PathVariable(value = "id") Long patronId,@RequestBody Patron patron){
        if(patronId == null) return ResponseEntity.ok(DataWrapperUtils.getWrapperError("You can't update record that has no id.",dw));
        Patron save = patronService.update(patronId,patron);
        return ResponseEntity.ok(DataWrapperUtils.getWrapper(save,dw));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataWrapper<String>> delete(@PathVariable(value = "id") Long patronId){
        patronService.deleteById(patronId);
        DataWrapper<String> dataWrapper = new DataWrapper<>();
        dataWrapper.setMessage("Data Deleted Successfully");
        return new ResponseEntity<>(dataWrapper, HttpStatus.OK);
    }
}
