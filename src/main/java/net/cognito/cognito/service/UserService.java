package net.cognito.cognito.service;

import net.cognito.cognito.model.User;
import net.cognito.cognito.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        // TODO: Handle user not found exception here.

        return user.get();
    }

    public User getUserById(String id) {
        Optional<User> user = userRepository.findById(id);

        // TODO: Handle user not found exception here.

        return user.get();
    }
}
// mongodb+srv://shreeram0312:9gK4Rbuu38dUJvgP@cognito-cluster.rgutnlv.mongodb.net/?retryWrites=true&w=majority&appName=cognito-cluster