package com.library.librarysystem.util.datawrapper;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
@Scope("prototype")
public class DataWrapper<T> {
    private String message;
    private boolean success;
    private Integer totalCount;
    private T data;
}
