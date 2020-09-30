package com.example.library.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class BookDTO extends BaseDTO<BookDTO> {

    private String title;
    private Integer qty;
}
