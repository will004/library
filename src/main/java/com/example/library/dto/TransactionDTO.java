package com.example.library.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TransactionDTO {

    private Long userId;
    private String deadlineDate;
    private String returnDate;

    private List<Long> bookIds;
}
