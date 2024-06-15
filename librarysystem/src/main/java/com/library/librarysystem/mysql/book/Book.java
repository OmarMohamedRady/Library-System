package com.library.librarysystem.mysql.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(catalog = "LIBRARY", schema = "LIBRARY",name = "BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    @NotNull(message = "Title is required")
    @NotBlank
    @Size(max = 100 ,message = "Length Should be max 100")
    private String title;
    @NotNull(message = "Author is required")
    @Size(max = 50 ,message = "Length Should be max 50")
    private String author;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd", timezone="Egypt",locale="ar_EG")
    private LocalDate publicationYear;
    @NotNull(message = "ISBN is required")
    @Size(max = 13,message = "Length Should be max 13")
    private String isbn;
    private Boolean isBorrowed = false;
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Integer start;
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Integer limit;
}
