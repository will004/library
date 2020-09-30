package com.example.library.service;

import com.example.library.dto.UserDTO;
import com.example.library.entity.User;
import com.example.library.exception.BaseErrorException;
import com.example.library.exception.ErrorMessage;
import com.example.library.mapper.UserMapper;
import com.example.library.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> findAllUser() {
        return userRepository.findAllByDeletedAtIsNull().stream().map(u -> {
            return new UserDTO()
                    .setId(u.getId())
                    .setName(u.getName())
                    .setEmail(u.getEmail())
                    .setCreatedAt(u.getCreatedAt().toString())
                    .setUpdatedAt(u.getUpdatedAt().toString());
        }).collect(Collectors.toList());
    }

    public UserDTO getUser(Long id) {
        User user = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BaseErrorException(HttpStatus.NOT_FOUND,
                        ErrorMessage.USER_NOT_FOUND.getMessage()));

        return convertToDto(user);
    }

    public UserDTO create(UserDTO request) {
        User user = convertToEntity(request);
        return convertToDto(userRepository.save(user));
    }

    public UserDTO update(Long id, UserDTO request) {
        User user = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BaseErrorException(HttpStatus.NOT_FOUND,
                        ErrorMessage.USER_NOT_FOUND.getMessage()));

        User updatedUser = user.setEmail(request.getEmail())
                .setName(request.getName());

        // update
        return convertToDto(userRepository.save(updatedUser));
    }

    public UserDTO delete(Long id){
        User user = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BaseErrorException(HttpStatus.NOT_FOUND,
                        ErrorMessage.USER_NOT_FOUND.getMessage()));

        // delete
        userRepository.save(user.setDeletedAt(Instant.now()));

        return convertToDto(user);
    }

    /*
    *
    * Private methods
    *
    * */

    private UserDTO convertToDto(User user){
        return userMapper.userToUserDTO(user);
    }

    private User convertToEntity(UserDTO userDTO){
        return userMapper.userDtoToUser(userDTO);
    }
}
