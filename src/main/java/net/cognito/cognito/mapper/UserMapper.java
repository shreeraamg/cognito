package net.cognito.cognito.mapper;

import net.cognito.cognito.dto.UserDto;
import net.cognito.cognito.model.User;

public class UserMapper {
    public static User mapToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setBio(userDto.getBio());
        user.setProfilePicture(userDto.getProfilePicture());
        user.setInterests(userDto.getInterests());
        user.setIsVerified(userDto.getIsVerified());

        return user;
    }
}
