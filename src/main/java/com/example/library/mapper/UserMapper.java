package com.example.library.mapper;

import com.example.library.dto.UserDTO;
import com.example.library.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);

    User userDtoToUser(UserDTO userDTO);
}
