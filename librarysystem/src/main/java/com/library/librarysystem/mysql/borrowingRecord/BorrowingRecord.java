package com.library.librarysystem.mysql.borrowingRecord;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.librarysystem.mysql.book.Book;
import com.library.librarysystem.mysql.patron.Patron;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(catalog = "LIBRARY",schema = "LIBRARY",name = "BORROWINGRECORD")
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowingRecordId;
    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "PATRON_ID")
    private Patron patron;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone="Egypt",locale="ar_EG")
    private LocalDateTime borrowingDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone="Egypt",locale="ar_EG")
    private LocalDateTime returnDate;
}
