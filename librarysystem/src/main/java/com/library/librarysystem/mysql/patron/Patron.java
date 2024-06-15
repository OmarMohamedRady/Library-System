package com.library.librarysystem.mysql.patron;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(catalog = "LIBRARY",schema = "LIBRARY",name = "PATRON")
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patronId;
    @NotNull(message = "Name is required")
    @Size(max = 50 , message = "Length Should be max 50")
    private String name;
    @NotNull
    @Size(max = 15 ,message = "Length Should be max 15")
    private String mobileNumber;
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Integer start;
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Integer limit;
}
