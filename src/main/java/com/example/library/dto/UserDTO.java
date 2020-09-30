package com.example.library.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class UserDTO extends BaseDTO<UserDTO> {

    private String name;
    private String email;

}
